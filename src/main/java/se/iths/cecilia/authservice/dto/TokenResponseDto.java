package se.iths.cecilia.authservice.dto;

import java.util.List;

/***
 * DTO for Token response. Returns accessToken (JWT), expiration time, subject and roles.
 * */

public record TokenResponseDto(
        String accessToken,
        long expiresIn,
        String subject,
        List<String> roles
) {
}
