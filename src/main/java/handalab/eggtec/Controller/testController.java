package handalab.eggtec.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class testController {

    @GetMapping("/test")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.status(HttpStatus.OK).body("asssss");
    }

}
