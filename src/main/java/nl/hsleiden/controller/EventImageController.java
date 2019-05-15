package nl.hsleiden.controller;

import nl.hsleiden.model.EventImage;
import nl.hsleiden.repository.EventImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class EventImageController {

    private final Logger LOGGER = LoggerFactory.getLogger(EventImageController.class);

    @Autowired
    private EventImageRepository eventImageRepo;

    @GetMapping("/api/eventimage")
    public Collection<EventImage> getEventImages() { return eventImageRepo.findAll(); }

    @GetMapping("/api/eventimage/{eventimageid}")
    public Optional<EventImage> getSpecificEventImage(@PathVariable Long eventImageId) {
        LOGGER.info("Fetching event image of id " + eventImageId);
        return eventImageRepo.findEventImageById(eventImageId);
    }
}
