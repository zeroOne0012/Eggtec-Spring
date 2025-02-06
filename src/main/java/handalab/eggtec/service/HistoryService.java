package handalab.eggtec.service;

import handalab.eggtec.dto.response.history.SummaryDTO;
import handalab.eggtec.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

//transactional, autowired
@Service
public class HistoryService {
    private final HistoryMapper historyMapper;
    public HistoryService(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }


    public List<SummaryDTO> summary() {
        return historyMapper.getSummary();
    }
}
