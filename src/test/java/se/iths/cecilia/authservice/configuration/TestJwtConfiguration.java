package se.iths.cecilia.authservice.configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@TestConfiguration
public class TestJwtConfiguration {

    @Bean
    public JwtDecoder jwtDecoder() {
        return Mockito.mock(JwtDecoder.class);
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return Mockito.mock(JwtEncoder.class);
    }

}
