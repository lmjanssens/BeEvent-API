package nl.hsleiden.repository;

import nl.hsleiden.model.RegisteredEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Robin Silverio
 */
@Repository
public interface RegisteredEventRepository extends JpaRepository<RegisteredEvent, Long> {
    List<RegisteredEvent> findAll();
}
