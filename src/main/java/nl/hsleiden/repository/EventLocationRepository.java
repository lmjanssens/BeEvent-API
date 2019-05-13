package nl.hsleiden.repository;

import nl.hsleiden.model.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
    List<EventLocation> findLocationById();
    List<EventLocation> findAll();
}
