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

import lombok.extern.slf4j.Slf4j;

/**
 * SocketIOController.
 */
@Component
@Slf4j
public class SocketIOController {

    private final List<Integer> socketPorts;
    private final Integer intervalSocketPort;
    private final List<Integer> noJsonSocketPorts;


    private final Map<Integer, SocketIOServer> servers;
    private final ObjectMapper objectMapper; // JSON 변환기
    private final IntervalService intervalService;

    /**
     * 소켓 이벤트 리스너 등록
     */
    public SocketIOController(Map<Integer, SocketIOServer> servers, ObjectMapper objectMapper, IntervalService intervalService
    ,@Value("#{'${socketio.server.port}'.split(',')}") List<String> ports
    ,@Value("${socketio.server.port.interval}") Integer intervalPort
    ,@Value("#{'${socketio.server.port.nojson}'.split(',')}") List<String> noJsonPorts) {
        socketPorts = ports.stream().map(Integer::parseInt).toList();
        noJsonSocketPorts = noJsonPorts.stream().map(Integer::parseInt).collect(Collectors.toList());
        // .map(s -> Integer.parseInt(s))
        intervalSocketPort = intervalPort;

        this.servers = servers;
        this.objectMapper = objectMapper;
        this.intervalService = intervalService;

        // 소켓 이벤트 리스너 등록
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
            log.info("Received message on port {}: {}", port, data);

            // *005 또는 *006 포트에서 실행 시 Broadcast
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
                // 에러 발생 시 DB 저장
                if (status!=null && status < 0) {
                    log.error("받은 에러: {}, {}", status,parsedData.getMessage());
                }

                // 모든 클라이언트에게 메시지 전송
                servers.get(port).getBroadcastOperations().sendEvent("message", data);
            } catch (Exception e) {
                log.error("Port {} - Database Error: {}", port, e.getMessage());
            }
        };
    }

    /**
     * 클라이언트 연결 리스너
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

//    /**
//     * 클라이언트 연결 해제 리스너
//     */
//    public DisconnectListener listenDisconnected() {
//        return client -> {
//            String sessionId = client.getSessionId().toString();
//            log.info("disconnect: " + sessionId);
//            client.disconnect();
//        };
//    }
}
