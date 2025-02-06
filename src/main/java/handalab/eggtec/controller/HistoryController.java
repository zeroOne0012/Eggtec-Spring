package handalab.eggtec.controller;

import handalab.eggtec.dto.response.history.SummaryDTO;
import handalab.eggtec.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/apis/history")
@RestController
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<SummaryDTO>> getSummary() {
        List<SummaryDTO> result = historyService.summary();

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // .build() 의미?
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
