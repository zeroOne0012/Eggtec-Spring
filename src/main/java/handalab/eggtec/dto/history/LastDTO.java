package handalab.eggtec.dto.history;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class LastDTO {
    private Integer recipeNo;
    private String lastDate;
    private Object ngCount;

    public List<Integer> getNgCountAsList() throws IOException{
        try{
            if(ngCount==null) return List.of();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(ngCount.toString(), new TypeReference<List<Integer>>(){});

        }catch(IOException e){
            throw e;
        }
    }

}
