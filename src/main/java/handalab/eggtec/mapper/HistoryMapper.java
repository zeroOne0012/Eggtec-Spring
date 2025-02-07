package handalab.eggtec.mapper;

import handalab.eggtec.dto.request.history.CsvFilterDTO;
import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.request.history.HistoryPostDTO;
import handalab.eggtec.dto.response.history.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {
//    @Select("SELECT * FROM history WHERE id = #{id}")
    List<HistoryTestDTO> getHistory(int id);

    List<TotalSummaryDTO> getTotalSummary();

    List<SummaryDTO> getSummary(@Param("id") Integer id, @Param("filter") HistoryFilterDTO filter); //Integer?

    List<LastDTO> getLast();

    List<TotalDTO> getTotal();

    List<CsvDTO> getCsvData(@Param("id") Integer id, @Param("filter") CsvFilterDTO info);

    Integer postHistory(@Param("history")HistoryPostDTO history);
}
