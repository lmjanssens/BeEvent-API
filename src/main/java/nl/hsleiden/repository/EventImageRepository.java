package nl.hsleiden.repository;

import nl.hsleiden.model.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {

    Optional<EventImage> findEventImageById(Long eventId);
    List<EventImage> findAll();
}
