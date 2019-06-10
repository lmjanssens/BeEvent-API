package nl.hsleiden.repository;

import nl.hsleiden.model.EmployeeEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeEmailRepository extends JpaRepository<EmployeeEmail, Long> {
    List<EmployeeEmail> findEmployeeEmailByEmployeeId(Long employeeEmailId);

    @Override
    List<EmployeeEmail> findAll();
}
