package handalab.eggtec.dto.response.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import handalab.eggtec.dto.JsonDTO;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.asm.TypeReference;

import java.io.IOException;
import java.util.List;

@Data
public class LastDTO {
    private Integer recipeNo;
    private String lastDate;
    private Object ngCount;
}
