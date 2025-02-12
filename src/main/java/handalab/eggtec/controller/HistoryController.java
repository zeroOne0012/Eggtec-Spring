package handalab.eggtec.controller;

import handalab.eggtec.dto.MessageDTO;
import handalab.eggtec.dto.request.history.CsvFilterDTO;
import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.response.history.HistoryDTO;
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
   public ResponseEntity<List<LastResponseDTO>> getLast(){
       List<LastResponseDTO> result = historyService.last();
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
    public ResponseEntity<MessageDTO> createHistory(@RequestBody HistoryDTO history) {
        HistoryDTO result = historyService.createHistory(history);
        return result!=null ? ResponseEntity.status(HttpStatus.CREATED).body(new MessageDTO("history 등록 성공","history", result))
                :ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDTO("Failed"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteHistory(@PathVariable(name="id") Integer id) {
        HistoryDTO result = historyService.deleteHistory(id);
        return result!=null ?ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("history 삭제 성공","deletedHistory", result))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDTO("Failed to delete history"));
    }
}
