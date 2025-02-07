package handalab.eggtec.dto.response.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvDTO {
    //@JsonProperty("recipe_no")
    private Integer recipeNo;
    private Integer ngCount;
//    @JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화 시 필요
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화 시 필요
//    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss") // 원하는 형태의 LocalDateTime 설정
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
}
