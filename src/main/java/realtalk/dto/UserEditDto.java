package realtalk.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Дто для изменения профиля
 */
@Data
public class UserEditDto {
    @Length(min = 3, max = 20)
    private String login;
    @Length(min = 3, max = 20)
    private String password;
    @Length(min = 1, max = 20)
    private String name;
    @Length(min = 3, max = 20)
    private String surname;
    private String photo;
}
