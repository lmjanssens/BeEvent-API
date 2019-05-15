package nl.hsleiden.repository;

import nl.hsleiden.model.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
    Optional<EventLocation> findLocationById(Long locationId);
    List<EventLocation> findAll();
}
