package realtalk.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private Date date;
    private UserDto user;
    private Long postId;
}
