package handalab.eggtec.dto.setting;

import lombok.Data;

// GET /apis/setting/initialize 쿼리1 - 선택된 레시피
@Data
public class SelectedDTO {
    private Integer idx;
    private String type;
    private String weight;
}
