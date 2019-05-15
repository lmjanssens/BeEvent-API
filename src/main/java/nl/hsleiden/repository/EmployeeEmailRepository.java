package nl.hsleiden.repository;

import nl.hsleiden.model.EmployeeEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeEmailRepository extends JpaRepository<EmployeeEmail, Long> {
    List<EmployeeEmail> findEmployeeEmailByEmployeeId(Long employeeEmailId);

    @Override
    List<EmployeeEmail> findAll();
}
