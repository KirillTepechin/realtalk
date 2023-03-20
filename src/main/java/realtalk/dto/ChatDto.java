package realtalk.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatDto {
    private Long id;
    private String name;
    private String image;
    private List<UserDto> users;
}
