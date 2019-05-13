package nl.hsleiden.repository;

import nl.hsleiden.model.CateringOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateringOrderRepository extends JpaRepository<CateringOrder, Long> {
}
