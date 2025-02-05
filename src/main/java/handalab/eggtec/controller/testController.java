package handalab.eggtec.controller;

import handalab.eggtec.dto.HistoryDTO;
import handalab.eggtec.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @Autowired
    private HistoryMapper historyMapper;

    @GetMapping("/test")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.status(HttpStatus.OK).body("asssss");
    }
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistory(@PathVariable("id") int id) {
        HistoryDTO history = historyMapper.getHistory(id);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

}
