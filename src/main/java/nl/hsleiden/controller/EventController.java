package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Event;
import nl.hsleiden.repository.CustomerRepository;
import nl.hsleiden.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class EventController {

    private final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventRepository eventRepo;

    @GetMapping("/api/events")
    public Collection<Event> getEvents() { return eventRepo.findAll(); }

    public Optional<Event> getSpecifiedEvents(@PathVariable Long eventId) {
        LOGGER.info("Fetching event object with id: " + eventId);
        return eventRepo.findById(eventId);
    }

    @PostMapping("/api/events")
    public Event createEvent(@Valid @RequestBody Event event){
        LOGGER.info("Creating event");
        return eventRepo.save(event);
    }

    @PutMapping
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
            event.setBuyNotes(updatedEvent.getBuyNotes());
            return eventRepo.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEvents(@PathVariable Long eventId) {
        LOGGER.info("Deleting event with id: " + eventId);
        return eventRepo.findById(eventId).map(event -> {
            eventRepo.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No event found with id " + eventId));
    }


}
