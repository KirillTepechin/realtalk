package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public abstract class MessageDto {
    private Long id;
    private UserDto user;
    private String text;
    @JsonFormat(pattern="dd.MM.yyyy")
    private Date date;

    public MessageAction getAction() {
        return null;
    }
}
