package se.iths.cecilia.authservice.dto;

import java.util.List;

public record SessionResponseDTO(
        Long expiresIn,
        String subject,
        List<String> roles
) {
}
