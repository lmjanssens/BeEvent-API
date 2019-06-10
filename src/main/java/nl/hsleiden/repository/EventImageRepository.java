package nl.hsleiden.repository;

import nl.hsleiden.model.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Robin Silverio
 */
@Repository
public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    List<EventImage> findAll();
}
