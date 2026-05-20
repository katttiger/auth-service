package se.iths.cecilia.authservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import se.iths.cecilia.authservice.repository.UserRepository;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final UserRepository userRepository;
    private final String jwtPublicKey;
    private final String jwtPrivateKey;
    private final String jwtKeyId;

    public SecurityConfig(
            UserRepository userRepository,
            @Value("app.jwt.private-key") String jwtPrivateKey,
            @Value("app.jwt.public-key") String jwtPublicKey,
            @Value("app.jwt.key-id") String jwtKeyId
    ) {
        this.userRepository = userRepository;
        this.jwtKeyId = jwtKeyId;
        this.jwtPrivateKey = jwtPrivateKey;
        this.jwtPublicKey = jwtPublicKey;
    }
}
