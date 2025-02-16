package handalab.eggtec.dto.history;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LastResponseDTO {
    private Integer recipeNo;
    private String lastDate;
    private List<Integer> ngCount;
}
