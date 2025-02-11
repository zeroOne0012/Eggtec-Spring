package handalab.eggtec.socketio;

import java.io.DataInput;
import java.util.List;
import java.util.Map;

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

//    @Value("${socketio.server.port.interval}")
//    private Integer intervalSocketPorts;
////    @Value("${socketio.server.port}")
//    @Value("#{'${socketio.server.port}'.split(',')}")
//    private List<Integer> socketPorts;
////    @Value("${socketio.server.port.no-json}")
//    @Value("#{'${socketio.server.port.no-json}'.split(',')}")
//    private List<Integer> noJsonSocketPorts;
    private final Integer intervalSocketPorts = 5004;
    private final List<Integer> socketPorts = List.of(5001, 5002, 5003, 5005, 5006);
    private final List<Integer> noJsonSocketPorts = List.of(5005,5006);

    private final Map<Integer, SocketIOServer> servers;
    private final ObjectMapper objectMapper; // JSON 변환기
    private final IntervalService intervalService;

    /**
     * 소켓 이벤트 리스너 등록
     */
    public SocketIOController(Map<Integer, SocketIOServer> servers, ObjectMapper objectMapper, IntervalService intervalService) {
        this.servers = servers;
        this.objectMapper = objectMapper;
        this.intervalService = intervalService;

        // 소켓 이벤트 리스너 등록
        for(SocketIOServer server : servers.values()) {
            int port = server.getConfiguration().getPort();
            if (socketPorts.contains(port)) {
                server.addEventListener("message", SocketMsgDTO.class, onMessage(port));
            } else if(intervalSocketPorts==port) {
                server.addConnectListener(onInterval(port));
                server.addEventListener("message", SocketMsgDTO.class, onStopMessage(port));
            }
        }
    }



    /**
     * message 리스너
     */
    public DataListener<SocketMsgDTO> onMessage(int port) {
        return (client, data, ackSender) -> {
            log.info("Received message on port {}: {}", port, data);

            // *005 또는 *006 포트에서 실행 시 Broadcast
            if (noJsonSocketPorts.contains(port)) {
                servers.get(port).getBroadcastOperations().sendEvent("message", data);
                return;
            }

                System.out.println("debug0" + data.toString());
            // 데이터베이스 연결 및 에러 처리
            try {
                // JSON 데이터 검증
                SocketMsgDTO parsedData;
                System.out.println("debug1");
                parsedData = objectMapper.readValue((DataInput) data, SocketMsgDTO.class);
                System.out.println("debug2");

                Integer status = (Integer) parsedData.getStatus();
                // 에러 발생 시 DB 저장
                if (status < 0) {
                    System.out.println("TEST2");
                    log.error("Status is negative: {}, {}", status,parsedData.getMessage());
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
//            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
//            log.info("connect:" + params.toString());
            log.info("interval is {}", intervalService.isRunning());
        };
    }

    // stop ?
    private DataListener<SocketMsgDTO> onStopMessage(int port) {
        return (client, data, ackSender) -> {
            try {
                // JSON 데이터 검증
                SocketMsgDTO parsedData;
                parsedData = objectMapper.readValue((DataInput) data, SocketMsgDTO.class);

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
