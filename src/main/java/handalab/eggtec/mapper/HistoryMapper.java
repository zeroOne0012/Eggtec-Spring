package handalab.eggtec.mapper;

import handalab.eggtec.dto.response.history.HistoryDTO;
import handalab.eggtec.dto.response.history.SummaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
//    @Select("SELECT * FROM history WHERE id = #{id}")
    HistoryDTO getHistory(int id);

    List<SummaryDTO> getSummary();
}
