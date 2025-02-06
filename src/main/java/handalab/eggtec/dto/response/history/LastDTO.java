package handalab.eggtec.dto.response.history;

import lombok.Data;

import java.util.List;

@Data
public class LastDTO {
    private Integer recipeNo;
    private String lastDate;
    private List<Integer> ngCount;
}
