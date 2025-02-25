package handalab.eggtec.dto.history;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// GET apis/history/last 응답 데이터(ngCount 가공 후)
@Data
@AllArgsConstructor
public class LastResponseDTO {
    private Integer recipeNo;
    private String lastDate;
    private List<Integer> ngCount;
}
