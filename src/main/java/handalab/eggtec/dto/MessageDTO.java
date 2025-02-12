package handalab.eggtec.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.util.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {
    private String message;

    private Map<String, Object> properties = new HashMap<>();

    public MessageDTO(String message) {
        this.message = message;
    }

    public MessageDTO(String message, String key, Object value) {
        this.message = message;
        this.properties.put(key, value);
    }

    @JsonAnyGetter // Map의 키를 JSON 속성으로 변환
    public Map<String, Object> getProperties() {
        return properties;
    }

    @JsonAnySetter
    public void addProperties(String key, String value) { properties.put(key, value); }
}
