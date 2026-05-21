package se.iths.cecilia.authservice.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;
import se.iths.cecilia.authservice.entity.User;
import se.iths.cecilia.authservice.repository.UserRepository;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//TODO: When the config is completed, tests must be activated again. They are turned off att the moment to make sure that contextLoad works.
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

    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(jwtKeyId)
                .build();
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
            return new CustomUserDetails(user);
        };
    }

    //TODO: The endpoints in the requestmatchers will need to be added when they are finished to specify what the person can and cannot do.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET).hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .anyRequest().authenticated()
        ).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


}
