package handalab.eggtec.service;

import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.response.history.FormattedSummaryDTO;
import handalab.eggtec.dto.response.history.SummaryDTO;
import handalab.eggtec.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

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


    public List<SummaryDTO> totalSummary() {
        return historyMapper.getTotalSummary();
    }


    public List<FormattedSummaryDTO> summaryByDate(Integer id, HistoryFilterDTO filter) {
        List<SummaryDTO> result = historyMapper.getSummary(id, filter);

        return result.stream()
                .map(dto-> new FormattedSummaryDTO(dto, LocalDate.now().toString()))
                .collect(Collectors.toList());
    }
}
