package nl.hsleiden.repository;

import nl.hsleiden.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByUserId(Long userId);

    @Override
    List<Instructor> findAll();
}
