package nl.hsleiden.repository;

import nl.hsleiden.model.Employee;
import nl.hsleiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUserId(Long userId);

    @Override
    List<Employee> findAll();
}
