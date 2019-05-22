package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Event;
import nl.hsleiden.repository.EventLocationRepository;
import nl.hsleiden.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

/**
 * Event Controller class
 * @author Robin Silverio
 */

@RestController
public class EventController {

    private final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private EventLocationRepository eventLocationRepo;



    /**
     * This is for getting all events from database.
     * @return a list of events
     */
    @GetMapping("/api/events")
    public Collection<Event> getEvents() { return eventRepo.findAll(); }

    /**
     * Retrieve a specific event by id
     * @param eventId id of event
     * @return a single specific event
     */
    @GetMapping("/api/events/{eventId}")
    public Optional<Event> getSpecifiedEvents(@PathVariable(value = "eventId") Long eventId) {
        LOGGER.info("Fetching event object with id: " + eventId);
        return eventRepo.findById(eventId);
    }

    /**
     * This is for inserting an event object to a database
     * @param eventLocationId id of a eventlocation stored in the database
     * @param event id of event stored in the database
     * @return an inserted event object
     */
    @PostMapping("/api/events/{eventLocationId}/{eventImageId}")
    public Event createEvent(@PathVariable(value = "eventLocationId") Long eventLocationId,
                             @Valid @RequestBody Event event){

        LOGGER.info("Creating event");
        return eventLocationRepo.findById(eventLocationId).map(eventLocation -> {
            event.setLocation(eventLocation);
            return eventRepo.save(event);
        }).orElseThrow(()-> new ResourceNotFoundException("No eventlocation found."));
    }

    /**
     * This is for updating data of event in the database
     * @param eventId id of event stored in database
     * @param updatedEvent a JSON-object obtained from the frontend ready to be inserted to the database
     * @return an updated event object
     */
    @PutMapping("/api/events/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @Valid @RequestBody Event updatedEvent) {
        return eventRepo.findById(eventId).map(event -> {
            event.setOwnEvent(updatedEvent.isOwnEvent());
            event.setName(updatedEvent.getName());
            event.setDescription(updatedEvent.getDescription());
            event.setProgram(updatedEvent.getProgram());
            event.setDuration(updatedEvent.getDuration());
            event.setOptions(updatedEvent.getOptions());
            event.setPricePerPerson(updatedEvent.getPricePerPerson());
            event.setPriceBuyPerPerson(updatedEvent.getPriceBuyPerPerson());
            event.setBtw(updatedEvent.getBtw());
            event.setNote(updatedEvent.getNote());
            return eventRepo.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
    }

    /**
     * This is for deleting a specific event object
     * @param eventId id of an event stored in the database
     * @return response
     */
    @DeleteMapping("/api/events/{eventId}")
    public ResponseEntity<?> deleteEvents(@PathVariable Long eventId) {
        LOGGER.info("Deleting event with id: " + eventId);
        return eventRepo.findById(eventId).map(event -> {
            eventRepo.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No event found with id " + eventId));
    }


}
