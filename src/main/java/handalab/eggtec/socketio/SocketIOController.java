package handalab.eggtec.socketio;

import java.io.DataInput;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import handalab.eggtec.dto.socketio.SocketMsgDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;

import handalab.eggtec.log.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * SocketIOController.
 */
@Component
@Slf4j
public class SocketIOController {
    private final Logger logger;

    private final List<Integer> socketPorts;
    private final Integer intervalSocketPort;
    private final List<Integer> noJsonSocketPorts;


    private final Map<Integer, SocketIOServer> servers;
    private final ObjectMapper objectMapper; // JSON 변환기
    private final IntervalService intervalService;

    /**
     * 소켓 이벤트 리스너 등록
     */
    public SocketIOController(Map<Integer, SocketIOServer> servers, ObjectMapper objectMapper, IntervalService intervalService, Logger logger
    ,@Value("#{'${socketio.server.port}'.split(',')}") List<String> ports
    ,@Value("${socketio.server.port.interval}") Integer intervalPort
    ,@Value("#{'${socketio.server.port.nojson}'.split(',')}") List<String> noJsonPorts) {
        socketPorts = ports.stream().map(Integer::parseInt).toList();
        noJsonSocketPorts = noJsonPorts.stream().map(Integer::parseInt).collect(Collectors.toList());
        intervalSocketPort = intervalPort;

        this.servers = servers;
        this.objectMapper = objectMapper;
        this.intervalService = intervalService;
        this.logger = logger;

        // 소켓별 이벤트 리스너 등록
        for(SocketIOServer server : servers.values()) {
            int port = server.getConfiguration().getPort();
            if (socketPorts.contains(port)) {
                server.addEventListener("message", String.class, onMessage(port));
            } else if(intervalSocketPort ==port) {
                server.addConnectListener(onInterval(port));
                server.addEventListener("message", String.class, onStopMessage(port));
            }
        }
    }

    /**
     * message 리스너
     */
    public DataListener<String> onMessage(int port) {
        return (client, data, ackSender) -> {
            // 4005 또는 4006 포트에서 실행 시 바로 브로드캐스트
            if (noJsonSocketPorts.contains(port)) {
                servers.get(port).getBroadcastOperations().sendEvent("message", data);
                return;
            }

            // 데이터베이스 연결 및 에러 처리
            try {
                // JSON 데이터 검증
                SocketMsgDTO parsedData;
                parsedData = objectMapper.readValue(data, SocketMsgDTO.class);

                Integer status = (Integer) parsedData.getStatus();
                // 에러 DB 저장
                if (status!=null && status < 0) {
                    logger.errLog("port("+port+")", parsedData.getMessage());
                }

                // 브로드캐스트
                servers.get(port).getBroadcastOperations().sendEvent("message", data);
            } catch (Exception e) {

               log.error("Port {} - Database Error: {}", port, e.getMessage());
            }
        };
    }

    /**
     * 클라이언트 연결 리스너 (interval start)
     */
    public ConnectListener onInterval(int port) {
        return (client) -> {
            if (!intervalService.isRunning()){
                intervalService.startInterval();
            }
            log.info("interval is {}", intervalService.isRunning());
        };
    }

    /**
     * message 리스너 (stop 요청)
     */
    private DataListener<String> onStopMessage(int port) {
        return (client, data, ackSender) -> {
            try {
                // JSON 데이터 검증
                SocketMsgDTO parsedData;
                parsedData = objectMapper.readValue(data, SocketMsgDTO.class);

                String message =  String.valueOf(parsedData.getMessage());
                if(message.equals("stop")) {
                    intervalService.stopInterval();
                }
            } catch (Exception e) {
                log.error("Port {} - Database Error: {}", port, e.getMessage());
            }
        };
    }

}
