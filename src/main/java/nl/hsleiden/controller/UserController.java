package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.User;
import nl.hsleiden.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users")
    @JsonView(View.Public.class)
    public Collection<User> getEmployees() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{employeeId}")
    public Optional<User> getEmployee(@PathVariable Long employeeId) {
        LOGGER.info("Fetching users with id" + employeeId);
        return userRepository.findById(employeeId);
    }

    @PostMapping("/api/users")
    public User createEmployee(@Valid @RequestBody User users) {
        LOGGER.info("Creating users.");
        return userRepository.save(users);
    }

    @PutMapping("/api/users/{employeeId}")
    public User updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody User updatedEmployee) {
        LOGGER.info("Updating users with id: " + employeeId);
        return userRepository.findById(employeeId).map(users -> {
//            users.setFirstName(updatedEmployee.getFirstName());
//            users.setInfix(updatedEmployee.getInfix());
//            users.setLastName(updatedEmployee.getLastName());
//            users.setEmails(updatedEmployee.getEmails());
//            users.setPhones(updatedEmployee.getPhones());

            return userRepository.save(users);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + employeeId));
    }

    @DeleteMapping("/api/users/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        LOGGER.info("Deleting customer with id: " + employeeId);
        return userRepository.findById(employeeId).map(users -> {
            userRepository.delete(users);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id" + employeeId));
    }
}
