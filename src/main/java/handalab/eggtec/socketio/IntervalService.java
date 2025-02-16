package handalab.eggtec.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import handalab.eggtec.dto.socketio.IntervalDTO;
import handalab.eggtec.mapper.SocketMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class IntervalService {

    @Value("${socketio.server.port.interval}")
    private Integer intervalPort;

    private final Map<Integer, SocketIOServer> socketIOServers;
    private final SocketMapper socketMapper;

    private final ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledTask;
    private boolean isRunning = false;

    public IntervalService(SocketMapper socketMapper, Map<Integer, SocketIOServer> socketIOServer) {
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
        this.socketMapper = socketMapper;
        this.socketIOServers = socketIOServer;
    }

    public void startInterval() {
        if (!isRunning) {
            scheduledTask = taskScheduler.scheduleAtFixedRate(this::executeTask, Duration.ofSeconds(3));
            isRunning = true;
            System.out.println("Interval started!");
        }
    }

    public void stopInterval() {
        if (scheduledTask != null && isRunning) {
            scheduledTask.cancel(true);
            isRunning = false;
            System.out.println("Interval stopped!");
        }
    }

    private void executeTask() {
        List<IntervalDTO> intervalDTO = socketMapper.getIntervalData();
        intervalDTO.getFirst().setLaneNo("all_total");
        socketIOServers.get(intervalPort).getBroadcastOperations().sendEvent("message", intervalDTO);
    }

    public boolean isRunning() {
        return isRunning;
    }
}
