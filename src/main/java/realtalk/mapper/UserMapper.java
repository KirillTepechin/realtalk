package realtalk.mapper;

import org.mapstruct.Mapper;
import realtalk.dto.*;
import realtalk.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromRegisterDto(RegisterDto dto);
    User fromLoginDto(LoginDto dto);
    UserEditDto toUserEditDto(User user);
    UserSubDto toUserSubDto(User user);
    UserDto toUserDto(User user);
}