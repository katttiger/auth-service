package se.iths.cecilia.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.cecilia.authservice.entity.User;

import java.util.Optional;

/**
 * JPA repositodry for User entity {@link se.iths.cecilia.authservice.entity.User}
 *
 */

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
