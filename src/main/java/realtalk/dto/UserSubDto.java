package realtalk.dto;

import lombok.Data;

import java.util.Set;

/**
 * Дто для получения подписчиков и подписок
 */
@Data
public class UserSubDto {
    private Set<UserDto> subscribers;
    private Set<UserDto> subscriptions;
}
