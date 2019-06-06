package nl.hsleiden.repository;

import nl.hsleiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserById(Long userId);

    User findByUsername(String username);

    @Override
    List<User> findAll();
}
