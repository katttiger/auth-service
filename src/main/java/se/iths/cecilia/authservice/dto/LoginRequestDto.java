package se.iths.cecilia.authservice.dto;

import jakarta.validation.constraints.NotBlank;

/***
 * DTO for login request. Takes username and password as parameters.
 * */
public record LoginRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
