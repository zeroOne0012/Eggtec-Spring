package handalab.eggtec.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvDTO {
    private Integer recipeNo;
    private Integer ngCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Data
    public static class CsvFilterDTO {
        @JsonProperty("start_date")
        private LocalDate startDate;
        @JsonProperty("end_date")
        private LocalDate endDate;
        private String path;
    }

    @Data
    public static class HistoryFilterDTO {
        @JsonProperty("start_date")
        private LocalDate startDate;
        @JsonProperty("end_date")
        private LocalDate endDate;
    }
}
