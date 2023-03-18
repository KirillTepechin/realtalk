package realtalk.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String text;
    private Date date;
    private UserDto user;
    private List<CommentDto> comments;
}