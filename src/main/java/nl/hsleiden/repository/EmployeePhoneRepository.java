package nl.hsleiden.repository;

import nl.hsleiden.model.EmployeePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePhoneRepository extends JpaRepository<EmployeePhone, Long> {
    List<EmployeePhone> findEmployeePhoneByEmployeeId(Long employeePhoneId);

    @Override
    List<EmployeePhone> findAll();
}
