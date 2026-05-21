package se.iths.cecilia.authservice.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.cecilia.authservice.dto.UserRequestDto;
import se.iths.cecilia.authservice.dto.UserResponseDto;
import se.iths.cecilia.authservice.entity.User;
import se.iths.cecilia.authservice.exceptions.UserNotFoundException;
import se.iths.cecilia.authservice.mapper.UserMapper;
import se.iths.cecilia.authservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> usersDto = new ArrayList<>();
        for (User currentUser : users) {
            UserResponseDto userResponseDto = new UserResponseDto(
                    currentUser.getId(), currentUser.getUsername()
            );
            usersDto.add(userResponseDto);
        }
        return usersDto;
    }


    public UserResponseDto findById(Long id) {
        User user = getAppUser(id);
        return userMapper.toResponseDto(user);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    public UserResponseDto updateUser(UserRequestDto userRequestDto, Long id) {
        User user = getAppUser(id);
        User convertedUser = userMapper.updateEntityFromDto(userRequestDto, user);
        if (convertedUser.getRole() == null) {
            convertedUser.setRole("USER");
        }
        User savedUser = userRepository.save(convertedUser);
        return userMapper.toResponseDto(user);

    }

    public void deleteUser(Long id) {
        User user = getAppUser(id);
        userRepository.delete(user);
    }

    private @NonNull User getAppUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " was not found."));
    }


}
