package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.RegisteredEvent;
import nl.hsleiden.repository.EventRepository;
import nl.hsleiden.repository.RegisteredEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Register event Controller
 * @author Robin Silverio
 */
@RestController
public class RegisteredEventController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegisteredEventController.class);

    @Autowired
    private RegisteredEventRepository registeredEventRepo;

    @Autowired
    private EventRepository eventRepo;

    /**
     * For retrieving a list of registered events stored in the database
     * @return a list of registered events
     */
    @GetMapping("/api/registeredevents")
    public Collection<RegisteredEvent> getRegisteredEvents() { return registeredEventRepo.findAll(); }

    /**
     * For retrieving a specific registered event object stored in the database
     * @param eventId id of event
     * @return a specific event object
     */
    @GetMapping("/api/registeredevents/{eventId}")
    public Optional<RegisteredEvent> getRegisteredEventById (@PathVariable Long eventId) {
        LOGGER.info("Fetching registered event with id " +  eventId);
        return registeredEventRepo.findById(eventId);
    }

    /**
     * For inserting an event object to a database
     * @param eventId id of event
     * @param event a JSON-object obtained from the frontend ready to be inserted to the database.
     * @return an inserted registered event object
     */
    @PostMapping("/api/registeredevents/{eventId}")
    public RegisteredEvent createRegisteredEvent(@PathVariable Long eventId,@Valid @RequestBody RegisteredEvent event){
        LOGGER.info("Creating registered event");
        return eventRepo.findEventById(eventId).map(event1 -> {
            event.setEvent(event1);
            return registeredEventRepo.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("No event found with id " + eventId));
    }

    /**
     * For deleting a specific registered event object
     * @param eventId id of event
     * @return response
     */
    @DeleteMapping("/api/registeredevents/{eventId}")
    public ResponseEntity<?> deleteEvents(@PathVariable Long eventId){
        LOGGER.info("Deleting registered event with id " + eventId);
        return registeredEventRepo.findById(eventId).map(event -> {
            registeredEventRepo.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No registered event found with id " + eventId));
    }

}
