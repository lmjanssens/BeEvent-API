package nl.hsleiden.repository;

import nl.hsleiden.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Robin Silverio
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();
}
