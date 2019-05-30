package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerOrderRepository")
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findCustomerOrderByCustomerOrderId(Long customerOrderId);

    @Override
    List<CustomerOrder> findAll();
}
