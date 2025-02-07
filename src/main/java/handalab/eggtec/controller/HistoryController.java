package handalab.eggtec.controller;

import handalab.eggtec.dto.MessageDTO;
import handalab.eggtec.dto.request.history.CsvFilterDTO;
import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.request.history.HistoryPostDTO;
import handalab.eggtec.dto.response.history.*;
import handalab.eggtec.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/apis/history")
@RestController
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<TotalSummaryDTO>> getTotalSummary() {
        List<TotalSummaryDTO> result = historyService.totalSummary();

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } // else null?
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/summary/{id}")
    public ResponseEntity<List<SummaryDTO>> getSummary(@PathVariable(name="id") Integer id, @RequestBody HistoryFilterDTO filter) {
        List<SummaryDTO> result = historyService.summaryByDate(id, filter);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/last")
    public ResponseEntity<List<LastDTO>> getLast(){
        List<LastDTO> result = historyService.last();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/total")
    public ResponseEntity<List<TotalDTO>> getTotal(){
        List<TotalDTO> result = historyService.total();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/csv/{id}")
    public ResponseEntity<MessageDTO> createCsv(@PathVariable(name="id") Integer id, @RequestBody CsvFilterDTO info) throws IOException {
        MessageDTO result = historyService.createCsv(id, info);
        return result!=null ? ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/")
    public ResponseEntity<HistoryPostDTO> createHistory(@RequestBody HistoryPostDTO history) {
        HistoryPostDTO result = historyService.createHistory(history);
        return result!=null ? ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
