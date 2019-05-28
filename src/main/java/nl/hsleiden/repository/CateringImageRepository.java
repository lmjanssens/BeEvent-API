package nl.hsleiden.repository;

import nl.hsleiden.model.CateringImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateringImageRepository extends JpaRepository<CateringImage, Long> {
    @Override
    List<CateringImage> findAll();
}
