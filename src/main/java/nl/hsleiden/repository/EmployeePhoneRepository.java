package nl.hsleiden.repository;

import nl.hsleiden.model.EmployeePhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeePhoneRepository extends JpaRepository<EmployeePhone, Long> {
    List<EmployeePhone> findEmployeePhoneByEmployeeId(Long employeePhoneId);

    @Override
    List<EmployeePhone> findAll();
}
