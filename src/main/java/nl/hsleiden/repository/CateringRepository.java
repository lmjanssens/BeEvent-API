package nl.hsleiden.repository;

import nl.hsleiden.model.Catering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateringRepository extends JpaRepository<Catering, Long> {
}
