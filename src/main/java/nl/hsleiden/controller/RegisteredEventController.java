package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.RegisteredEvent;
import nl.hsleiden.repository.RegisteredEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RegisteredEventController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegisteredEventController.class);

    @Autowired
    private RegisteredEventRepository registeredEventRepo;

    @GetMapping("/api/registeredevents")
    public Collection<RegisteredEvent> getRegisteredEvents() { return registeredEventRepo.findAll(); }

    @GetMapping("/api/registeredevents/{event_id}")
    public Optional<RegisteredEvent> getRegisteredEventById (@PathVariable Long eventId) {
        LOGGER.info("Fetching registered event with id " +  eventId);
        return registeredEventRepo.findById(eventId);
    }

    @PostMapping("/api/registeredevents")
    public RegisteredEvent createRegisteredEvent(@Valid @RequestBody RegisteredEvent event){
        LOGGER.info("Creating registered event");
        return registeredEventRepo.save(event);
    }

    @DeleteMapping("/api/registeredevents/{event_id}")
    public ResponseEntity<?> deleteEvents(@PathVariable Long eventId){
        LOGGER.info("Deleting registered event with id " + eventId);
        return registeredEventRepo.findById(eventId).map(event -> {
            registeredEventRepo.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No registered event found with id " + eventId));
    }

}
