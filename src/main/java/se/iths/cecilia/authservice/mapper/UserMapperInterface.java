package se.iths.cecilia.authservice.mapper;

import se.iths.cecilia.authservice.dto.UserRequestDto;
import se.iths.cecilia.authservice.dto.UserResponseDto;
import se.iths.cecilia.authservice.entity.User;

/**
 * Interface for mapper {@link se.iths.cecilia.authservice.mapper.UserMapper}
 *
 */
public interface UserMapperInterface {

    User toEntity(UserRequestDto requestDto);

    UserResponseDto toResponseDto(User user);

    User updateEntityFromDto(UserRequestDto userRequestDto, User user);

}
