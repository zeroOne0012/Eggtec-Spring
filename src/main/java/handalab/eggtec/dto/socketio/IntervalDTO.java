package handalab.eggtec.dto.socketio;

import lombok.Data;

// interval 소켓으로 주는 요약 정보
@Data
public class IntervalDTO {
    private Object laneNo;
    private Integer ngCountToday;
    private Integer okCountToday;
    private Integer total;
}
