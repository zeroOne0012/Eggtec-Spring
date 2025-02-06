package handalab.eggtec.dto.response.history;

import lombok.Data;

@Data
public class TotalSummaryDTO { // list
    private Integer recipeNo;
    private String nickname;
    private String type;
    private String weight;
    private Double ngPercentage;
}