package handalab.eggtec.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값이면 JSON에서 제외
public class HistoryDTO {
    private Integer idx;

    @JsonProperty("recipe_no")
    private Integer recipeNo;

    private Integer lane1;
    private Integer lane2;
    private Integer lane3;
    private Integer lane4;
    private Integer lane5;
    private Integer lane6;

    @JsonProperty("item_no")
    private Long itemNo;
    @JsonProperty("created_t")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdT;
}
