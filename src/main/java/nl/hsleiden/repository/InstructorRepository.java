package nl.hsleiden.repository;

import nl.hsleiden.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findInstructorById(Long employeeId);

    @Override
    List<Instructor> findAll();
}
