package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Employee;
import nl.hsleiden.repository.EmployeeEmailRepository;
import nl.hsleiden.repository.EmployeePhoneRepository;
import nl.hsleiden.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/api/employees")
    public Collection<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/api/employees/{employeeId}")
    public Optional<Employee> getEmployee(@PathVariable Long employeeId) {
        LOGGER.info("Fetching employee with id" + employeeId);
        return employeeRepository.findById(employeeId);
    }

    @PostMapping("/api/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        LOGGER.info("Creating employee.");
        return employeeRepository.save(employee);
    }

    @PutMapping("/api/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody Employee updatedEmployee) {
        LOGGER.info("Updating employee with id: " + employeeId);
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setInfix(updatedEmployee.getInfix());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setEmails(updatedEmployee.getEmails());
            employee.setPhones(updatedEmployee.getPhones());

            return employeeRepository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
    }

    @DeleteMapping("/api/employees/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        LOGGER.info("Deleting customer with id: " + employeeId);
        return employeeRepository.findById(employeeId).map(employee -> {
            employeeRepository.delete(employee);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id" + employeeId));
    }
}
