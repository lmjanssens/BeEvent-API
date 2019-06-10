package nl.hsleiden.repository;

import nl.hsleiden.model.CateringOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CateringOrderRepository extends JpaRepository<CateringOrder, Long> {
    @Override
    List<CateringOrder> findAll();
}
