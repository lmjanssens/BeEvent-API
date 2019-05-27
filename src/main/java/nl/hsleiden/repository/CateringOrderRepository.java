package nl.hsleiden.repository;

import nl.hsleiden.model.CateringOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateringOrderRepository extends JpaRepository<CateringOrder, Long> {
    @Override
    List<CateringOrder> findAll();
}
