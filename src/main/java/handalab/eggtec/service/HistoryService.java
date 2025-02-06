package handalab.eggtec.service;

import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.response.history.SummaryDTO;
import handalab.eggtec.dto.response.history.LastDTO;
import handalab.eggtec.dto.response.history.TotalSummaryDTO;
import handalab.eggtec.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//transactional, autowired
@Service
public class HistoryService {
    private final HistoryMapper historyMapper;
    public HistoryService(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }


    public List<TotalSummaryDTO> totalSummary() {
        return historyMapper.getTotalSummary();
    }


    public List<SummaryDTO> summaryByDate(Integer id, HistoryFilterDTO filter) {
        List<SummaryDTO> result = historyMapper.getSummary(id, filter);

        return result.stream()
                .peek(dto-> dto.setDate(dto.getDate().split("T")[0]))
                .collect(Collectors.toList());
    }

    public List<LastDTO> last() {
        List<LastDTO> result = historyMapper.getLast();

        return result.stream()
                .peek(dto -> dto.setLastDate(dto.getLastDate().split("T")[0]))
                .collect(Collectors.toList());
    }
}
