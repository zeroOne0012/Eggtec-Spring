package handalab.eggtec.mapper;

import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.response.history.HistoryDTO;
import handalab.eggtec.dto.response.history.SummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {
//    @Select("SELECT * FROM history WHERE id = #{id}")
    HistoryDTO getHistory(int id);

    List<SummaryDTO> getTotalSummary();
    List<SummaryDTO> getSummary(@Param("id") Integer id, @Param("filter") HistoryFilterDTO filter); //Integer?
}
