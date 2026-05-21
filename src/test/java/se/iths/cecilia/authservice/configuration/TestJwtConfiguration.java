package se.iths.cecilia.authservice.configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@TestConfiguration
@WebMvcTest
public class TestJwtConfiguration {

    public JwtDecoder jwtDecoder() {
        return Mockito.mock(JwtDecoder.class);
    }

    public JwtEncoder jwtEncoder() {
        return Mockito.mock(JwtEncoder.class);
    }

}
