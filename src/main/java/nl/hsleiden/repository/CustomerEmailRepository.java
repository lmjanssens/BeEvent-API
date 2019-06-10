package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerEmailRepository extends JpaRepository<CustomerEmail, Long> {
    @Query(value = "SELECT e.email " +
            "FROM customer_email e " +
            "JOIN customer c ON e.customerid = c.customerid " +
            "WHERE c.email = :email", nativeQuery = true)
    Optional<CustomerEmail> findEmailByAddress(@Param("email") String email);

    List<CustomerEmail> findEmployeeEmailByCustomerId(Long customerEmailId);

    @Override
    List<CustomerEmail> findAll();
}
