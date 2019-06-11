package nl.hsleiden.controller;

import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.EventImage;
import nl.hsleiden.repository.EventImageRepository;
import nl.hsleiden.repository.EventRepository;
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
public class EventImageController {

    private final Logger LOGGER = LoggerFactory.getLogger(EventImageController.class);

    @Autowired
    private EventImageRepository eventImageRepo;

    @Autowired
    private EventRepository eventRepo;

    /**
     * For retrieving a list of imagepaths from the database
     * @return a list of imagepaths
     */
    @GetMapping("/api/eventimage")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Collection<EventImage> getEventImages() { return eventImageRepo.findAll(); }

    /**
     * For retrieving a specific imagepath
     * @param eventImageId id of event image
     * @return a specific event image object
     */
    @GetMapping("/api/eventimage/{eventImageId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Optional<EventImage> getSpecificEventImage(@PathVariable Long eventImageId) {
        LOGGER.info("Fetching event image of id " + eventImageId);
        return eventImageRepo.findById(eventImageId);
    }

    /**
     * For inserting an image object to a database
     * @param eventId id of event
     * @param eventImage a JSON-object obtained from the frontend ready to be inserted to the database
     * @return an inserted event image object
     */
    @PostMapping(value = "/api/eventimage/{eventId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public EventImage createEventImage(@PathVariable(value = "eventId") Long eventId,
                                       @Valid @RequestBody EventImage eventImage)
    {
        LOGGER.info("Creating event image");
        return eventRepo.findById(eventId).map(event -> {
            eventImage.setEvent(event);
            return eventImageRepo.save(eventImage);
        }).orElseThrow(() -> new ResourceNotFoundException("No event found with id " + eventId));
    }

    /**
     * For updating an event image object
     * @param eventImageId id of event image
     * @param eventImage JSON-object retrieved from the backend ready to be updated to the database
     * @return An updated event image object
     */
    @PutMapping(value = "/api/eventimage/{eventImageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public EventImage updateEventImage(@PathVariable Long eventImageId, @Valid @RequestBody EventImage eventImage) {
        LOGGER.info("Updating event image of id " + eventImageId);
        return eventImageRepo.findById(eventImageId).map(updateEventImage -> {
            updateEventImage.setImagePath(eventImage.getImagePath());
            return eventImageRepo.save(updateEventImage);
        }).orElseThrow(() -> new ResourceNotFoundException("Cannot find an event image of id " + eventImageId));
    }

    /**
     * For deleting a specific event image object
     * @param eventImageId id of event image
     * @return response
     */
    @DeleteMapping("/api/eventimage/{eventImageId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteEventImage(@PathVariable Long eventImageId) {
        return eventImageRepo.findById(eventImageId).map(eventImage -> {
            eventImageRepo.delete(eventImage);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No event image found with id " + eventImageId));
    }

}
