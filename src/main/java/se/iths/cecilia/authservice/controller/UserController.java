package se.iths.cecilia.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.cecilia.authservice.dto.UserRequestDto;
import se.iths.cecilia.authservice.dto.UserResponseDto;
import se.iths.cecilia.authservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> returnAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @RequestBody @Valid UserRequestDto userRequestDto,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRequestDto, id));
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
