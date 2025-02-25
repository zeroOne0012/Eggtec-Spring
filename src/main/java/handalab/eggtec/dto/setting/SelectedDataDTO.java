package handalab.eggtec.dto.setting;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

// GET /apis/setting/initialize 쿼리2 - 선택된 레시피 정보 + 세팅 정보
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
