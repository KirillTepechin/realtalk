package realtalk.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Дто для страницы пользователя
 */
@Data
public class UserProfileInfoDto {
    private String login;
    private String name;
    private String surname;
    private String photo;
    private String city;
    private Date borthdate;
    private Set<UserDto> subscribers;
    private Set<UserDto> subscriptions;
}