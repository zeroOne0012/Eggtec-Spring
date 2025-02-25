package handalab.eggtec.dto.socketio;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 소켓으로 주고 받는 메시지
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketMsgDTO {
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;
}
