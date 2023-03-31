package realtalk.dto;

import lombok.Data;

@Data
public class MessageOnUpdateDto extends MessageDto{
    @Override
    public MessageAction getAction() {
        return MessageAction.ON_UPDATE;
    }
}
