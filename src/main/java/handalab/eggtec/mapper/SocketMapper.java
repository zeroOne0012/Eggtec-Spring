package handalab.eggtec.mapper;

import handalab.eggtec.dto.socketio.IntervalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SocketMapper {

    List<IntervalDTO> getIntervalData();
}
