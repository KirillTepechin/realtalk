package realtalk.mapper;

import org.mapstruct.*;
import realtalk.dto.*;
import realtalk.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User fromRegisterDto(RegisterDto dto);

    User fromLoginDto(LoginDto dto);
    User fromUserProfileInfoDto(UserProfileInfoDto userProfileInfoDto);

    UserEditDto toUserEditDto(User user);

    UserDto toUserDto(User user);

    UserProfileInfoDto toUserProfileInfoDto(User user);
}