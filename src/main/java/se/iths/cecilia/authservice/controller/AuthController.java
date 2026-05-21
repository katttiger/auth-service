package se.iths.cecilia.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.cecilia.authservice.dto.LoginRequestDto;
import se.iths.cecilia.authservice.dto.TokenResponseDto;
import se.iths.cecilia.authservice.service.AuthService;

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

}
