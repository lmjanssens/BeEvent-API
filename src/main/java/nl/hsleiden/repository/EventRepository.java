package nl.hsleiden.repository;

import nl.hsleiden.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Robin Silverio
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();
}
