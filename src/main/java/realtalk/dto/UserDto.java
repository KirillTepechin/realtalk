package realtalk.dto;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UserDto {
    @Length(min = 1, max = 20)
    private String name;
    @Length(min = 3, max = 20)
    private String surname;
}
