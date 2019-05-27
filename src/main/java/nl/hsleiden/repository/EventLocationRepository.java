package nl.hsleiden.repository;

import nl.hsleiden.model.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Robin Silverio
 */
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
    @Override
    List<EventLocation> findAll();
}
