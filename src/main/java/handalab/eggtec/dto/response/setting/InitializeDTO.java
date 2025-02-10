package handalab.eggtec.dto.response.setting;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InitializeDTO {
    @JsonProperty("RECIPE_IDX")
    private Integer RECIPE_IDX;
    @JsonProperty("RECIPE_NET")
    private String RECIPE_NET;
    @JsonProperty("CAM1")
    private List<Integer> CAM1;
    @JsonProperty("CAM2")
    private List<Integer> CAM2;
    @JsonProperty("EXPOSURE")
    private Integer EXPOSURE;
    @JsonProperty("COUNTER")
    private Integer COUNTER;
}
