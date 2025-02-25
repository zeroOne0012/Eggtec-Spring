package handalab.eggtec.mapper;

import handalab.eggtec.dto.history.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<TotalSummaryDTO> getTotalSummary();

    List<SummaryDTO> getSummary(@Param("id") Integer id, @Param("filter") HistoryFilterDTO filter); //Integer?

    List<LastDTO> getLast();

    List<TotalDTO> getTotal();

    List<CsvDTO> getCsvData(@Param("id") Integer id, @Param("filter") CsvFilterDTO info);

    HistoryDTO postHistory(@Param("history") HistoryDTO history);

    HistoryDTO deleteHistory(Integer id);
}
