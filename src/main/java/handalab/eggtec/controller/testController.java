package handalab.eggtec.controller;

import handalab.eggtec.dto.response.history.HistoryTestDTO;
import handalab.eggtec.mapper.HistoryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {


    // 필드 주입보다 권장되는 생성자 주입 방식
    // Spring 프레임워크에서는 생성자 주입을 적극 지원해 생성자가 1개만 있을 경우에 @Autowired를 생략해도 주입이 가능하도록 편의성을 제공하고 있다.
    private final HistoryMapper historyMapper;
    //@Autowired
    public testController(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }



    @GetMapping("/test")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.status(HttpStatus.OK).body("testAPI");
    }
    @GetMapping("/history-test/{id}")
    public ResponseEntity<List<HistoryTestDTO>> getHistory(@PathVariable("id") int id) {
        List<HistoryTestDTO> history = historyMapper.getHistory(id);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

}
