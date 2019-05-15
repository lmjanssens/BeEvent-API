package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.EventLocation;
import nl.hsleiden.repository.EventLocationRepository;
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
public class EventLocationController {
    private final Logger LOGGER = LoggerFactory.getLogger(EventLocationController.class);

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @GetMapping("/api/eventlocation")
    private Collection<EventLocation> getEventLocations() { return eventLocationRepository.findAll(); }

    @GetMapping("/api/eventlocation/{locationId}")
    public Optional<EventLocation> getSpecifiedEventlocation(@PathVariable  Long locationId) {
        LOGGER.info("Fetching event location with id: "  + locationId);
        return eventLocationRepository.findLocationById(locationId);
    }

    @PostMapping("/api/eventlocation")
    public EventLocation createEventLocation(@Valid @RequestBody EventLocation location) {
        LOGGER.info("Creating new eventlocation.");
        return eventLocationRepository.save(location);
    }

    @PutMapping("/api/eventlocation/{locationId}")
    public EventLocation updateEventLocation(@PathVariable Long locationId, @Valid @RequestBody EventLocation updatedEventLocation) {
        LOGGER.info("Updating eventlocation with id: " + locationId);
        return eventLocationRepository.findLocationById(locationId).map(eventLocation -> {
            eventLocation.setName(updatedEventLocation.getName());
            eventLocation.setDescription(eventLocation.getDescription());
            eventLocation.setRoutepicture(eventLocation.getRoutepicture());
            return eventLocationRepository.save(eventLocation);
        }).orElseThrow(() -> new ResourceNotFoundException("Event location not found with id " + locationId));
    }

    @DeleteMapping("/api/eventlocation/{locationId}")
    public ResponseEntity<?> deleteEventLocation(@PathVariable Long locationId) {
        LOGGER.info("Deleting event location with id " + locationId);
        return eventLocationRepository.findLocationById(locationId).map(eventLocation -> {
            eventLocationRepository.delete(eventLocation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event location not found with id" + locationId));
    }
}
