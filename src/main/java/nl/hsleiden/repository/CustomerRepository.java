package nl.hsleiden.repository;

import nl.hsleiden.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findCustomerById(Long customerId);

    @Override
    List<Customer> findAll();
}
