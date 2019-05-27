package nl.hsleiden.repository;

import nl.hsleiden.model.Catering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateringRepository extends JpaRepository<Catering, Long> {
    @Override
    List<Catering> findAll();
}
