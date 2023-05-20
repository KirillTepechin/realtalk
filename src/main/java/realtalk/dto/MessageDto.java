package realtalk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class MessageDto {
    private int id;
    private UserDto userDto;
    private String text;
    private Date date;

    public MessageAction getAction() {
        return null;
    }
}
