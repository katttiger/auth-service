package se.iths.cecilia.authservice.dto;

import java.util.List;

/***
 * DTO for sessionresponse. Returns JWT token with subject (user), roles (admin/user) and time before the JWT expires and the user is logged out.
 * */

public record SessionResponseDTO(
        Long expiresIn,
        String subject,
        List<String> roles
) {
}
