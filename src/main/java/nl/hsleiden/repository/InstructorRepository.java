package nl.hsleiden.repository;

import nl.hsleiden.model.Instructor;
import nl.hsleiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByUserId(Long userId);

    Optional<Instructor> findByUser(User user);

    Instructor findByUserId(int user);

    @Override
    List<Instructor> findAll();
}
