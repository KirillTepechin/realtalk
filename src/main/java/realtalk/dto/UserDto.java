package realtalk.dto;

import lombok.Data;

/**
 * Дто для упрощения информации о пользователе
 */
@Data
public class UserDto {
    private String login;
    private String name;
    private String surname;
    private String photo;
}
