package handalab.eggtec.dto.socketio;

import lombok.Data;

@Data
public class IntervalDTO {
    private Object laneNo;
    private Integer ngCountToday;
    private Integer okCountToday;
    private Integer total;
}
