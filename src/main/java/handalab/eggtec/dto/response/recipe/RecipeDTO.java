package handalab.eggtec.dto.response.recipe;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeDTO {
    private Integer idx;
    private String nickname;
    private String type;
    private String weight;
    private Boolean selected;
}
