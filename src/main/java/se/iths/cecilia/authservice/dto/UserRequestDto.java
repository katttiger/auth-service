package se.iths.cecilia.authservice.dto;

/***
 * DTO for user request. Takes name and password.
 * */

public record UserRequestDto(
        String name,
        String password) {
}
