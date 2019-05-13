package nl.hsleiden.repository;

import nl.hsleiden.model.Customer;
import nl.hsleiden.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventById();
    List<Event> findAll();
}
