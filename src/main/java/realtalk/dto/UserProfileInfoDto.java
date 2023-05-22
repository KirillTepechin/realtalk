package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Дто для страницы пользователя
 */
@Data
public class UserProfileInfoDto {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String photo;
    private String city;
    @JsonFormat(pattern="dd.MM.yyyy")
    private Date borthdate;
    private Set<UserDto> subscribers;
    private Set<UserDto> subscriptions;
}