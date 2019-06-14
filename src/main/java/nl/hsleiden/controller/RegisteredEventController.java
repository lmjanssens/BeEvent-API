package nl.hsleiden.controller;

import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.*;
import nl.hsleiden.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
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

    @Autowired
    private InstructorRepository instructorRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * For retrieving a list of registered events stored in the database
     * @return a list of registered events
     */
    @GetMapping("/api/registeredevents")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    public Collection<RegisteredEvent> getRegisteredEvents() { return registeredEventRepo.findAll(); }

    /**
     * For retrieving a registered event by instructor username
     * @return a subscribed event
     */
    @GetMapping("/api/registeredevents/orderid/{orderId}")
    @PreAuthorize("hasAuthority('" + Role.INSTRUCTOR + "')")
    public List<RegisteredEvent> getRegisteredEventByOrder (@PathVariable Long orderId) {
        LOGGER.info("Fetching registered event by orderid " + orderId);
        return registeredEventRepo.findByOrder_OrderId(orderId);
    }

    /**
     * For retrieving a specific registered event object stored in the database
     * @param eventId id of event
     * @return a specific event object
     */
    @GetMapping("/api/registeredevents/{eventId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Optional<RegisteredEvent> getRegisteredEventById (@PathVariable Long eventId) {
        LOGGER.info("Fetching registered event with id " +  eventId);
        return registeredEventRepo.findById(eventId);
    }

    /**
     * For inserting an event object to a database
     * @param eventId id of event
     * @param registeredEvent a JSON-object obtained from the frontend ready to be inserted to the database.
     * @return an inserted registered event object
     */
    @PostMapping("/api/registeredevents/{orderId}/{eventId}/{instructorId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    public RegisteredEvent createRegisteredEvent(@PathVariable Long orderId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable String instructorId,
                                                        @Valid @RequestBody RegisteredEvent registeredEvent){
        LOGGER.info("Creating registered event");

        User user = userRepository.findByUsername(instructorId);

        return eventRepo.findById(eventId).map(event1 -> {
            registeredEvent.setEvent(event1);
            return orderRepository.findById(orderId).map(order -> {
                registeredEvent.setOrder(order);
                return instructorRepo.findByUser(user).map(instructor -> {
                    registeredEvent.setInstructor(instructor);
                    return registeredEventRepo.save(registeredEvent);
                }).orElseThrow(() -> new ResourceNotFoundException("No instructor found of id " + instructorId));
            }).orElseThrow(() -> new ResourceNotFoundException("Could not find order of id " + orderId));
        }).orElseThrow(() -> new ResourceNotFoundException("No event found of id " + eventId));

    }

    /**
     * For deleting a specific registered event object
     * @param eventId id of event
     * @return response
     */
    @DeleteMapping("/api/registeredevents/{eventId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteEvents(@PathVariable Long eventId){
        LOGGER.info("Deleting registered event with id " + eventId);
        return registeredEventRepo.findById(eventId).map(event -> {
            registeredEventRepo.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No registered event found with id " + eventId));
    }
}
