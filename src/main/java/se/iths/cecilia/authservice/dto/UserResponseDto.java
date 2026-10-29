package se.iths.cecilia.authservice.dto;

/***
 * DTO for user response. Returns user id and username.
 * */

public record UserResponseDto(
        Long id,
        String username
) {
}
