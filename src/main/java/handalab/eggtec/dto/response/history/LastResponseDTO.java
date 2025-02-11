package handalab.eggtec.dto.response.history;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import handalab.eggtec.dto.JsonDTO;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
public class LastResponseDTO {
    private Integer recipeNo;
    private String lastDate;
    private List<Integer> ngCount;
}
