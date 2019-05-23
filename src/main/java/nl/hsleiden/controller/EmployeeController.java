package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Employee;
import nl.hsleiden.model.EmployeeEmail;
import nl.hsleiden.model.EmployeePhone;
import nl.hsleiden.repository.EmployeeEmailRepository;
import nl.hsleiden.repository.EmployeePhoneRepository;
import nl.hsleiden.repository.EmployeeRepository;
import nl.hsleiden.service.CollectionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class EmployeeController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEmailRepository employeeEmailRepository;

    @Autowired
    EmployeePhoneRepository employeePhoneRepository;

    CollectionDataService<EmployeeEmail> emailCollectionDataService = new CollectionDataService<>();
    CollectionDataService<EmployeePhone> phoneCollectionDataService = new CollectionDataService<>();

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
        Employee savedEmployee = employeeRepository.save(employee);

        Collection<EmployeeEmail> employeeEmails = employee.getEmails();
        Collection<EmployeePhone> employeePhones = employee.getPhones();

        this.saveEmailAddresses(savedEmployee, employeeEmails);
        this.savePhoneNumbers(savedEmployee, employeePhones);

        return savedEmployee;
    }

    @PutMapping("/api/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody Employee updatedEmployee) {
        LOGGER.info("Updating employee with id: " + employeeId);
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setInfix(updatedEmployee.getInfix());
            employee.setLastName(updatedEmployee.getLastName());

            Collection<EmployeeEmail> emailsToSave = emailCollectionDataService.getToBeSaved(employee.getEmails(), updatedEmployee.getEmails());
            Collection<EmployeeEmail> emailsToDelete = emailCollectionDataService.getToBeDeleted(employee.getEmails(), updatedEmployee.getEmails());

            Collection<EmployeePhone> phonesToSave = phoneCollectionDataService.getToBeSaved(employee.getPhones(), updatedEmployee.getPhones());
            Collection<EmployeePhone> phonesToDelete = phoneCollectionDataService.getToBeDeleted(employee.getPhones(), updatedEmployee.getPhones());

            saveEmailAddresses(employee, emailsToSave);
            deleteEmailAddresses(emailsToDelete);

            savePhoneNumbers(employee, phonesToSave);
            deletePhoneNumbers(phonesToDelete);

            Set<EmployeeEmail> employeeEmails = new HashSet<>(emailCollectionDataService.mergedCollection(
                    emailCollectionDataService.substractCollection(
                            employee.getEmails(), emailsToDelete
                    ), emailsToSave
            ));

            Set<EmployeePhone> employeePhones = new HashSet<>(phoneCollectionDataService.mergedCollection(
                    phoneCollectionDataService.substractCollection(
                            employee.getPhones(), phonesToDelete
                    ), phonesToSave
            ));

            employee.setEmails(employeeEmails);
            employee.setPhones(employeePhones);

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
