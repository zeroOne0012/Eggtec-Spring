package handalab.eggtec.controller;

import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.response.history.FormattedSummaryDTO;
import handalab.eggtec.dto.response.history.SummaryDTO;
import handalab.eggtec.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/apis/history")
@RestController
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<SummaryDTO>> getTotalSummary() {
        List<SummaryDTO> result = historyService.totalSummary();

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // .build() 의미?
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/summary/{id}")
    public ResponseEntity<List<FormattedSummaryDTO>> getSummary(@PathVariable(name="id") Integer id, @RequestBody HistoryFilterDTO filter) {
        List<FormattedSummaryDTO> result = historyService.summaryByDate(id, filter);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
