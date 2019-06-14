package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.EventLocation;
import nl.hsleiden.repository.EventLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class EventLocationController {

    private final Logger LOGGER = LoggerFactory.getLogger(EventLocationController.class);

    @Autowired
    private EventLocationRepository eventLocationRepository;

    /**
     * For retrieving a list of locations
     * @return a list of locations
     */
    @GetMapping("/api/eventlocation")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    private Collection<EventLocation> getEventLocations() { return eventLocationRepository.findAll(); }

    /**
     * For getting a specific location from a database
     * @param locationId id of location
     * @return a single location object
     */
    @GetMapping("/api/eventlocation/{locationId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Optional<EventLocation> getSpecifiedEventlocation(@PathVariable  Long locationId) {
        LOGGER.info("Fetching event location with id: "  + locationId);
        return eventLocationRepository.findById(locationId);
    }

    /**
     * For inserting a location object to the database
     * @param location a JSON-object obtained from the frontend ready to be inserted in the database.
     * @return an inserted event location object
     */
    @PostMapping(value = "/api/eventlocation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public EventLocation createEventLocation(@Valid @RequestBody EventLocation location) {
        LOGGER.info("Creating new eventlocation.");
        return eventLocationRepository.save(location);
    }

    /**
     * For updating a location object
     * @param locationId id of location
     * @param updatedEventLocation a JSON-object obtained from the frontend ready to be updated in the database
     * @return an updated location object
     */
    @PutMapping(value = "/api/eventlocation/{locationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public EventLocation updateEventLocation(@PathVariable Long locationId, @Valid @RequestBody EventLocation updatedEventLocation) {
        LOGGER.info("Updating eventlocation with id: " + locationId);
        return eventLocationRepository.findById(locationId).map(eventLocation -> {
            eventLocation.setName(updatedEventLocation.getName());
            eventLocation.setDescription(updatedEventLocation.getDescription());
            eventLocation.setRoutePicture(updatedEventLocation.getRoutePicture());
            return eventLocationRepository.save(eventLocation);
        }).orElseThrow(() -> new ResourceNotFoundException("Event location not found with id " + locationId));
    }

    /**
     * For deleting a single location object
     * @param locationId id of a location
     * @return response
     */
    @DeleteMapping("/api/eventlocation/{locationId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteEventLocation(@PathVariable Long locationId) {
        LOGGER.info("Deleting event location with id " + locationId);
        return eventLocationRepository.findById(locationId).map(eventLocation -> {
            eventLocationRepository.delete(eventLocation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event location not found with id" + locationId));
    }
}
