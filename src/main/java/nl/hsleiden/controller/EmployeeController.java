package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Employee;
import nl.hsleiden.model.EmployeeEmail;
import nl.hsleiden.model.EmployeePhone;
import nl.hsleiden.repository.EmployeeEmailRepository;
import nl.hsleiden.repository.EmployeePhoneRepository;
import nl.hsleiden.repository.EmployeeRepository;
import nl.hsleiden.service.CollectionDataService;
import nl.hsleiden.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class EmployeeController {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEmailRepository employeeEmailRepository;

    @Autowired
    private EmployeePhoneRepository employeePhoneRepository;

    @Autowired
    private UserService userService;

    CollectionDataService<EmployeeEmail> emailCollectionDataService = new CollectionDataService<>();
    CollectionDataService<EmployeePhone> phoneCollectionDataService = new CollectionDataService<>();

    @GetMapping("/api/employees")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/api/employees/{employeeId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<Employee> getEmployee(@PathVariable Long employeeId) {
        LOGGER.info("Fetching employee with id" + employeeId);
        return employeeRepository.findById(employeeId);
    }

    @PostMapping("/api/employees")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        LOGGER.info("Creating employee.");

        employee.setUser(
                userService.encodePassword(employee.getUser())
        );

        Employee savedEmployee = employeeRepository.save(employee);

        this.saveEmailAddresses(savedEmployee, employee.getEmails());
        this.savePhoneNumbers(savedEmployee, employee.getPhoneNumbers());

        return savedEmployee;
    }

    @PutMapping("/api/employees/{employeeId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Employee updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody Employee updatedEmployee) {
        LOGGER.info("Updating employee with id: " + employeeId);
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setInfix(updatedEmployee.getInfix());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setNote(updatedEmployee.getNote());

            Collection<EmployeeEmail> emailsToSaved = emailCollectionDataService.getToBeSaved(employee.getEmails(), updatedEmployee.getEmails());
            Collection<EmployeeEmail> emailsToDeletes = emailCollectionDataService.getToBeDeleted(employee.getEmails(), updatedEmployee.getEmails());

            Collection<EmployeePhone> phonesToSaved = phoneCollectionDataService.getToBeSaved(employee.getPhoneNumbers(), updatedEmployee.getPhoneNumbers());
            Collection<EmployeePhone> phonesToDeleted = phoneCollectionDataService.getToBeDeleted(employee.getPhoneNumbers(), updatedEmployee.getPhoneNumbers());

            saveEmailAddresses(employee, emailsToSaved);
            deleteEmailAddresses(emailsToDeletes);

            savePhoneNumbers(employee, phonesToSaved);
            deletePhoneNumbers(phonesToDeleted);

            employee.setEmails(
                    emailCollectionDataService.getDefinitiveCollection(employee.getEmails(), emailsToSaved, emailsToDeletes)
            );
            employee.setPhoneNumbers(
                    phoneCollectionDataService.getDefinitiveCollection(employee.getPhoneNumbers(), phonesToSaved, phonesToDeleted)
            );

            return employeeRepository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
    }

    @DeleteMapping("/api/employees/{employeeId}")
    @PreAuthorize("hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        LOGGER.info("Deleting customer with id: " + employeeId);
        return employeeRepository.findById(employeeId).map(employee -> {
            employeeRepository.delete(employee);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id" + employeeId));
    }

    private void saveEmailAddresses(Employee employee, Collection<EmployeeEmail> toBeSaved) {
        try {
            for (EmployeeEmail email : toBeSaved)
                email.setEmployee(employee);

            employeeEmailRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save e-mail addresses");
        }
    }

    private void deleteEmailAddresses(Collection<EmployeeEmail> toBeDeleted) {
        try {
            employeeEmailRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete e-mail addresses");
        }
    }

    private void savePhoneNumbers(Employee employee, Collection<EmployeePhone> toBeSaved) {
        try {
            for (EmployeePhone phone : toBeSaved)
                phone.setEmployee(employee);

            employeePhoneRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save phone numbers");
        }
    }

    private void deletePhoneNumbers(Collection<EmployeePhone> toBeDeleted) {
        try {
            employeePhoneRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete phone numbers");
        }
    }
}
