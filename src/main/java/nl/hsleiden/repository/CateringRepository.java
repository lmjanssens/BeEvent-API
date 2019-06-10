package nl.hsleiden.repository;

import nl.hsleiden.model.Catering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CateringRepository extends JpaRepository<Catering, Long> {
    @Override
    List<Catering> findAll();
}
