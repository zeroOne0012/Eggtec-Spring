package handalab.eggtec.dto.response.recipe;

import lombok.Data;

@Data
public class RecipeDTO {
    private Integer idx;
    private String nickname;
    private String type;
    private String weight;
    private Boolean selected;
}
