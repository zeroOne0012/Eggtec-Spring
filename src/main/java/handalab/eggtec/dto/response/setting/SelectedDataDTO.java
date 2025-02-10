package handalab.eggtec.dto.response.setting;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectedDataDTO {
    private Integer outputCnt;
    private Integer exposure;
    private Integer y1;
    private Integer y2;
    private Integer y3;
    private Integer y4;

}
