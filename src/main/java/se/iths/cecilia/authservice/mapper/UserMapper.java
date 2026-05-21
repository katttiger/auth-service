package se.iths.cecilia.authservice.mapper;

import org.springframework.stereotype.Component;
import se.iths.cecilia.authservice.dto.UserRequestDto;
import se.iths.cecilia.authservice.dto.UserResponseDto;
import se.iths.cecilia.authservice.entity.User;

@Component
public class UserMapper implements UserMapperInterface {
    @Override
    public User toEntity(UserRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.name());
        user.setPassword(requestDto.password());
        return user;
    }

    @Override
    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername()
        );
    }

    @Override
    public User updateEntityFromDto(UserRequestDto userRequestDto, User user) {
        if (userRequestDto == null) {
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userRequestDto.name());
        newUser.setPassword(userRequestDto.password());
        return newUser;
    }
}
