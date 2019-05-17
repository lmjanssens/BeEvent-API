package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerEmailRepository extends JpaRepository<CustomerEmail, Long> {
    @Query(value = "SELECT e.email " +
            "FROM customer_email e " +
            "JOIN customer c ON e.customerid = c.customerid " +
            "WHERE c.email = :email", nativeQuery = true)
    Optional<CustomerEmail> findEmailByAddress(@Param("email") String email);
}
