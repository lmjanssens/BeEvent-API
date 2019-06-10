package nl.hsleiden.repository;

import nl.hsleiden.model.CateringImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CateringImageRepository extends JpaRepository<CateringImage, Long> {
    @Override
    List<CateringImage> findAll();
}
