package handalab.eggtec.dto.history;

import lombok.Data;

import java.time.LocalDate;

// GET /apis/history/total 응답
@Data
public class TotalDTO {
    private int recipeNo;
    private String ngTotalCount;
}
