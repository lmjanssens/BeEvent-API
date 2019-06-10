package nl.hsleiden.repository;

import nl.hsleiden.model.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Robin Silverio
 */
@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
    @Override
    List<EventLocation> findAll();
}
