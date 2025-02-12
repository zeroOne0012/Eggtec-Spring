package handalab.eggtec.dto.setting;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettingDTO {
    private Integer idx;
    @JsonProperty("output_cnt")
    private Integer outputCnt;
    private String title;
    @JsonProperty("exp_white")
    private Integer expWhite;
    @JsonProperty("exp_brown")
    private Integer expBrown;
    private Integer y1;
    private Integer y2;
    private Integer y3;
    private Integer y4;
}
