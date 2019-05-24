package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findCustomerOrderByCustomerOrderId(Long customerOrderId);

    @Override
    List<CustomerOrder> findAll();
}
