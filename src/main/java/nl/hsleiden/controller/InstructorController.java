package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Instructor;
import nl.hsleiden.model.Instructor;
import nl.hsleiden.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class InstructorController {

    private final Logger LOGGER = LoggerFactory.getLogger(InstructorController.class);

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("/api/instructors")
    public Collection<Instructor> getInstructors() {
        return instructorRepository.findAll();
    }

    @GetMapping("/api/instructors/{instructorId}")
    public Optional<Instructor> getInstructor(@PathVariable Long instructorId) {
        LOGGER.info("Fetching instructor with id" + instructorId);
        return instructorRepository.findById(instructorId);
    }

    @PostMapping("/api/instructors")
    public Instructor createInstructor(@Valid @RequestBody Instructor instructor) {
        LOGGER.info("Creating instructor.");
        return instructorRepository.save(instructor);
    }

    @PutMapping("/api/instructors/{instructorId}")
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
    public ResponseEntity<?> deleteInstructor(@PathVariable Long instructorId) {
        LOGGER.info("Deleting instructor with id: " + instructorId);
        return instructorRepository.findById(instructorId).map(instructor -> {
            instructorRepository.delete(instructor);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id" + instructorId));
    }
}
