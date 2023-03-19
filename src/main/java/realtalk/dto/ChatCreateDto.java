package realtalk.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatCreateDto {
    private String name;
    //TODO:возможен ли пустой список?
    private List<Long> userIds;
}
