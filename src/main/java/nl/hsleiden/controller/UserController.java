package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Instructor;
import nl.hsleiden.model.LoginUser;
import nl.hsleiden.model.User;
import nl.hsleiden.repository.InstructorRepository;
import nl.hsleiden.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("/api/users")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<User> getEmployees() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{userId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')  or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<User> getEmployee(@PathVariable Long userId) {
        LOGGER.info("Fetching users with id" + userId);
        return userRepository.findById(userId);
    }

    @PostMapping("/api/users")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public User createEmployee(@Valid @RequestBody User users) {
        LOGGER.info("Creating users.");
        users.setPassword(bCryptPasswordEncoder().encode(users.getPassword()));
        return userRepository.save(users);
    }

    @PutMapping("/api/users/{userId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public User updateEmployee(@PathVariable Long userId, @Valid @RequestBody User updatedUser) {
        LOGGER.info("Updating users with id: " + userId);
        return userRepository.findById(userId).map(user -> {
            user.setPassword(bCryptPasswordEncoder().encode(updatedUser.getPassword()));
            user.setActions(updatedUser.getActions());

            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/api/users/{userId}")
    @PreAuthorize("hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long userId) {
        LOGGER.info("Deleting customer with id: " + userId);
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));
    }

    @GetMapping("/api/users/me")
    public LoginUser login () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        String authority = null;
        User user = null;
        Instructor instructor = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
            for (GrantedAuthority a : authentication.getAuthorities()){
                authority = a.toString();
            }
        }

        user = userRepository.findByUsername(username);

        if (authority.equals(Role.INSTRUCTOR)){
            instructor = instructorRepository.findByUserId(user.getId());
            user.setId(instructor.getId());
        }

        return new LoginUser(user.getId(), username, authority);
    }

    private PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
