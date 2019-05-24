package nl.hsleiden.repository;

import nl.hsleiden.model.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Robin Silverio
 */
public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    List<EventImage> findAll();
}
