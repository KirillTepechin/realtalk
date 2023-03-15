package realtalk.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import realtalk.mapper.UserMapper;
import realtalk.mapper.UserMapperImpl;
import realtalk.model.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Дто для получения подписчиков и подписок
 */
@Setter
@NoArgsConstructor
public class UserSubDto {
    private UserMapper userMapper = new UserMapperImpl(); //Нельзя заинжектить, тк класс UserSubDto не является бином, имплементация сгенерированна mapstruct`ом
    private Set<User> subscribers;
    private Set<User> subscriptions;

    /**
     * Самописный(что бы избежать sof) геттер для подписчиков
     * @return Множество обьектов UserDto
     */
    public Set<UserDto> getSubscribers() {
        return subscribers.stream().map(sub->userMapper.toUserDto(sub)).collect(Collectors.toSet());
    }
    /**
     * Самописный(что бы избежать sof) геттер для подписок
     * @return Множество обьектов UserDto
     */
    public Set<UserDto> getSubscriptions() {
        return subscriptions.stream().map(sub->userMapper.toUserDto(sub)).collect(Collectors.toSet());

    }
}
