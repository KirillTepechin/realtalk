package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String text;
    private Date date;
    private UserDto user;
    private List<CommentDto> comments;
    @JsonIgnore
    private Set<UserDto> likes;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int likesCount;
    public int getLikesCount() {
        return likes.size();
    }
}