package nl.hsleiden.repository;

import nl.hsleiden.model.RegisteredEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Robin Silverio
 */
public interface RegisteredEventRepository extends JpaRepository<RegisteredEvent, Long> {

    List<RegisteredEvent> findByOrder_OrderId (Long orderId);
    List<RegisteredEvent> findAll();
}
