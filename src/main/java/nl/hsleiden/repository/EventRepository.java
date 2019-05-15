package nl.hsleiden.repository;

import nl.hsleiden.model.Customer;
import nl.hsleiden.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventById(Long eventLocation);
    List<Event> findAll();
}
