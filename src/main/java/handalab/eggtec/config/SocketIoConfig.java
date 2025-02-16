package handalab.eggtec.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SocketIoConfig {

    @Value("${socketio.server.port}")
    private List<Integer> ports;
    @Value("${socketio.server.port.interval}")
    private Integer i_port;

    @Bean
    public Map<Integer, SocketIOServer> socketIOServers(){
        Map<Integer, SocketIOServer> servers = new HashMap<>();
        for(Integer port : ports){
            servers.put(port, createSocketIoServer(port));
        }
        servers.put(i_port, createSocketIoServer(i_port));

        return servers;
    }


    private SocketIOServer createSocketIoServer(Integer port) {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(port);
        return new SocketIOServer(config);
    }
}
