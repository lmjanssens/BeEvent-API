package nl.hsleiden.repository;

import nl.hsleiden.model.Employee;
import nl.hsleiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findCustomerById(Long employeeId);

    @Override
    List<Employee> findAll();
}
