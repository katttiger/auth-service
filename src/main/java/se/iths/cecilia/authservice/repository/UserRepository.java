package se.iths.cecilia.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.cecilia.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
}
