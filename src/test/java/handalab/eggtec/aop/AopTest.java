package handalab.eggtec.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import handalab.eggtec.mapper.HistoryMapper;
import handalab.eggtec.service.HistoryService;

@Import(AspectLog3.class)
// @Import(AspectLog.class)
@SpringBootTest
public class AopTest {
    @Autowired
    HistoryService historyService;
    @Autowired
    HistoryMapper historyMapper;
    
    @Test
    void succes(){
        historyService.totalSummary();
        historyService.summaryByDate(1, null);
        historyService.last();
    }
}
