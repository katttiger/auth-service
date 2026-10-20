package se.iths.cecilia.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.iths.cecilia.authservice.dto.LoginRequestDto;
import se.iths.cecilia.authservice.dto.SessionResponseDTO;
import se.iths.cecilia.authservice.dto.TokenResponseDto;
import se.iths.cecilia.authservice.service.AuthService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        System.out.println("RAW POST: /auth/login body= " + request);
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/jwks")
    public ResponseEntity<Map<String, Object>> publicJwks() {
        return ResponseEntity.ok(authService.publicJwkSet());
    }

    @GetMapping("/me")
    public ResponseEntity<SessionResponseDTO> me(
            @AuthenticationPrincipal Jwt jwt
    ) {
        List<String> roles = jwt.getClaimAsStringList("roles");

        SessionResponseDTO response = new SessionResponseDTO(
                authService.remainingSeconds(jwt),
                jwt.getSubject(),
                roles == null ? List.of() : roles
        );

        return ResponseEntity.ok(response);
    }
}
