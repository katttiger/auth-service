package se.iths.cecilia.authservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.util.StringUtils;
import se.iths.cecilia.authservice.repository.UserRepository;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Profile("!test")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final String jwtPublicKey;
    private final String jwtPrivateKey;
    private final String jwtKeyId;

    public SecurityConfig(
            UserRepository userRepository,
            @Value("${app.jwt.key-id}") String jwtKeyId,
            @Value("${app.jwt.private-key}") String jwtPrivateKey,
            @Value("${app.jwt.public-key}") String jwtPublicKey
    ) {
        this.userRepository = userRepository;
        this.jwtKeyId = jwtKeyId;
        this.jwtPrivateKey = jwtPrivateKey;
        this.jwtPublicKey = jwtPublicKey;
    }


    @Bean
    public KeyPair keyPair() throws Exception {

        System.out.println("Private key " + jwtPrivateKey);
        System.out.println("Public key " + jwtPublicKey);

        if (StringUtils.hasText(jwtPrivateKey) && StringUtils.hasText(jwtPublicKey)) {
            byte[] privateBytes = Base64.getDecoder().decode(jwtPrivateKey);
            byte[] publicBytes = Base64.getDecoder().decode(jwtPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));
            return new KeyPair(publicKey, privateKey);
        }

        throw new IllegalArgumentException("Private or public key for JWT missing");
    }

}
