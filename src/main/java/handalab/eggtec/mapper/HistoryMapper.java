package handalab.eggtec.mapper;

import handalab.eggtec.dto.HistoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {
    HistoryDTO getHistory(int id);
}
