package nl.hsleiden.repository;

import nl.hsleiden.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findInstructorById(Long employeeId);

    @Override
    List<Instructor> findAll();
}
