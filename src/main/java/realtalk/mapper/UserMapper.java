package realtalk.mapper;

import org.mapstruct.Mapper;
import realtalk.dto.LoginDto;
import realtalk.dto.RegisterDto;
import realtalk.dto.UserDto;
import realtalk.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromRegisterDto(RegisterDto dto);
    User fromLoginDto(LoginDto dto);
    User fromUserDto(UserDto dto);
    UserDto toUserDto(User user);
}