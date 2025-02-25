package handalab.eggtec.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

// csv 데이터 쿼리 결과
@Data
public class CsvDTO {
    private Integer recipeNo;
    private Integer ngCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;



}
