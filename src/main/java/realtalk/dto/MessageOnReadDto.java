package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;
@Data
public class MessageOnReadDto extends MessageDto{
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Set<UserDto> readBy;
    @Override
    public MessageAction getAction() {
        return MessageAction.ON_READ;
    }
}
