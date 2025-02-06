package handalab.eggtec.dto.response.history;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SummaryDTO { // list
    private Integer recipeNo;
    private String nickname;
    private String type;
    private String weight;
    private Double ngPercentage;
}