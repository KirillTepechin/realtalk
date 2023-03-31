package realtalk.dto;

import lombok.Data;

import java.util.Date;
@Data
public abstract class MessageDto {
    private Long id;
    private UserDto userDto;
    private String text;
    private Date date;
    public abstract MessageAction getAction();
}
