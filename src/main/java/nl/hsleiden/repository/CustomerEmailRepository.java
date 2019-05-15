package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerEmailRepository extends JpaRepository<CustomerEmail, Long> {
    @Query("SELECT c.email FROM customer c WHERE c.email = :email")
    Optional<CustomerEmail> findEmailByAddress(@Param("email") String email);
}
