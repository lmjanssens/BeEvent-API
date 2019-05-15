package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.EventImage;
import nl.hsleiden.repository.EventImageRepository;
import nl.hsleiden.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Collection<EventImage> getEventImages() { return eventImageRepo.findAll(); }

    /**
     * For retrieving a specific imagepath
     * @param eventImageId id of event image
     * @return a specific event image object
     */
    @GetMapping("/api/eventimage/{eventimageid}")
    public Optional<EventImage> getSpecificEventImage(@PathVariable Long eventImageId) {
        LOGGER.info("Fetching event image of id " + eventImageId);
        return eventImageRepo.findEventImageById(eventImageId);
    }

    /**
     * For inserting an image object to a database
     * @param eventId id of event
     * @param eventImage a JSON-object obtained from the frontend ready to be inserted to the database
     * @return an inserted event image object
     */
    @PostMapping("/api/eventImage/{eventId}")
    public EventImage createEventImage(@PathVariable(value = "eventId") Long eventId,
                                       @Valid @RequestBody EventImage eventImage)
    {
        LOGGER.info("Creating event image");
        return eventRepo.findEventById(eventId).map(event -> {
            eventImage.setEvent(event);
            return eventImageRepo.save(eventImage);
        }).orElseThrow(() -> new ResourceNotFoundException("No event found with id " + eventId));
    }

    /**
     * For deleting a specific event image object
     * @param eventImageId id of event image
     * @return response
     */
    @DeleteMapping("/api/eventimage/{eventImageId}")
    public ResponseEntity<?> deleteEventImage(@PathVariable Long eventImageId) {
        return eventImageRepo.findEventImageById(eventImageId).map(eventImage -> {
            eventImageRepo.delete(eventImage);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No event image found with id " + eventImageId));
    }

}
