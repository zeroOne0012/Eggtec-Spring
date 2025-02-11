package handalab.eggtec.socketio;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.HashMap;
import java.util.Map;

/**
 * SocketIoServerLifeCycle.
 *
 * <p>
 *   SocketIoServer의 생명주기를 관리
 * </p>
 */
@Component
public class SocketIoServerLifeCycle {
    private final Map<Integer, SocketIOServer> servers;

    public SocketIoServerLifeCycle(Map<Integer, SocketIOServer> servers) {
        this.servers = servers;
    }

    /**
     * SocketIo 서버 시작
     */
    @PostConstruct
    public void start() {
        for (SocketIOServer server : servers.values()) {
            server.start();
        }
    }

    /**
     * SocketIo 서버 종료
     */
    @PreDestroy
    public void stop() {
        for (SocketIOServer server : servers.values()) {
            server.stop();
        }
    }
}
