package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private Date date;
    private UserDto user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long postId;
}
