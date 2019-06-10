package nl.hsleiden.repository;

import nl.hsleiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserById(Long userId);

    @Override
    List<User> findAll();
}
