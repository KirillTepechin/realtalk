package realtalk.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatCreateDto {
    private String name;
    private List<Long> userIds;
}
