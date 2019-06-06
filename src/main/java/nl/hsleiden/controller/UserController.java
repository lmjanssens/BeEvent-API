package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.User;
import nl.hsleiden.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users")
    @JsonView(View.Public.class)
    @RolesAllowed(Role.ADMIN)
    public Collection<User> getEmployees() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{userId}")
    @RolesAllowed(Role.ADMIN)
    public Optional<User> getEmployee(@PathVariable Long userId) {
        LOGGER.info("Fetching users with id" + userId);
        return userRepository.findById(userId);
    }

    @PostMapping("/api/users")
    @RolesAllowed(Role.ADMIN)
    public User createEmployee(@Valid @RequestBody User users) {
        LOGGER.info("Creating users.");
        return userRepository.save(users);
    }

    @PutMapping("/api/users/{userId}")
    @RolesAllowed(Role.ADMIN)
    public User updateEmployee(@PathVariable Long userId, @Valid @RequestBody User updatedUser) {
        LOGGER.info("Updating users with id: " + userId);
        return userRepository.findById(userId).map(user -> {
            user.setPassword(updatedUser.getPassword());
            user.setActions(updatedUser.getActions());

            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/api/users/{userId}")
    @RolesAllowed(Role.ADMIN)
    public ResponseEntity<?> deleteEmployee(@PathVariable Long userId) {
        LOGGER.info("Deleting customer with id: " + userId);
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));
    }
}
