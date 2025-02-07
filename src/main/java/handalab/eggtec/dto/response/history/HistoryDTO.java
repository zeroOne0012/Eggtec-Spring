package handalab.eggtec.dto.response.history;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDTO {
    private Integer idx;
    private Integer recipeNo;

    private Integer lane1;
    private Integer lane2;
    private Integer lane3;
    private Integer lane4;
    private Integer lane5;
    private Integer lane6;

    private Long itemNo;
    private LocalDateTime createdT;
}
