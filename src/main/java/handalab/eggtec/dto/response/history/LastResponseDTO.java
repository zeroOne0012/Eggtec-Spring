package handalab.eggtec.dto.response.history;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import handalab.eggtec.dto.JsonDTO;
import jakarta.annotation.PostConstruct;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class LastResponseDTO {
    private Integer recipeNo;
    private String lastDate;
    private List<Integer> ngCount;

//    public LastResponseDTO(LastDTO lastDTO) {
//        this.recipeNo = lastDTO.getRecipeNo();
//        this.lastDate = lastDTO.getLastDate();
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            List<Integer> list = objectMapper.readValue(lastDTO.getNgCount().getValue(), new TypeReference<List<Integer>>() {});
//            this.ngCount = list;
//            System.out.println(list);  // 출력: [11, 0, 0, 0, ...]
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
