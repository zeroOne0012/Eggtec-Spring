package handalab.eggtec.dto.history;

import lombok.Data;

// POST /apis/history/summary/:id 응답
@Data
public class SummaryDTO { // list
    private Integer recipeNo;
    private String date;
    private String ngCount;
}