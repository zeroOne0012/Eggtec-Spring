package handalab.eggtec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private Long idx;          // SERIAL (자동 증가 ID)
    private Integer recipeNo;  // FOREIGN KEY (recipe 테이블 참조)
    private Integer laneNo;    // INT (라인 번호)
    private Long itemNo;       // BIGINT (아이템 번호)
    private String result;     // VARCHAR (기본값 'NG')
    private LocalDateTime createdT; // TIMESTAMP (생성 시간)
}