package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Instructor;
import nl.hsleiden.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class InstructorController {

    private final Logger LOGGER = LoggerFactory.getLogger(InstructorController.class);

    @Autowired
    private InstructorRepository instructorRepository;

//    @Autowired
//    private UserService userService;

    @GetMapping("/api/instructors")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<Instructor> getInstructors() {
        return instructorRepository.findAll();
    }

    @GetMapping("/api/instructors/{instructorId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<Instructor> getInstructor(@PathVariable Long instructorId) {
        LOGGER.info("Fetching instructor with id" + instructorId);
        return instructorRepository.findById(instructorId);
    }

    @PostMapping("/api/instructors")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Instructor createInstructor(@Valid @RequestBody Instructor instructor) {
        LOGGER.info("Creating instructor.");
//        instructor.setUser(
//                this.userService.encodePassword(instructor.getUser())
//        );

        return instructorRepository.save(instructor);
    }

    @PutMapping("/api/instructors/{instructorId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Instructor updateInstructor(@PathVariable Long instructorId, @Valid @RequestBody Instructor updatedInstructor) {
        LOGGER.info("Updating instructor with id: " + instructorId);
        return instructorRepository.findById(instructorId).map(instructor -> {
            instructor.setFirstName(updatedInstructor.getFirstName());
            instructor.setInfix(updatedInstructor.getInfix());
            instructor.setLastName(updatedInstructor.getLastName());
            instructor.setEmail(updatedInstructor.getEmail());
            instructor.setPhoneNumber(updatedInstructor.getPhoneNumber());

            return instructorRepository.save(instructor);
        }).orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id " + instructorId));
    }

    @DeleteMapping("/api/instructors/{instructorId}")
    @PreAuthorize("hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteInstructor(@PathVariable Long instructorId) {
        LOGGER.info("Deleting instructor with id: " + instructorId);
        return instructorRepository.findById(instructorId).map(instructor -> {
            instructorRepository.delete(instructor);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id" + instructorId));
    }
}
