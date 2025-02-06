package handalab.eggtec.dto.request.history;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryFilterDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
