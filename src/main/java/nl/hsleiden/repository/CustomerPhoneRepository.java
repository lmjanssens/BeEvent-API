package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerPhoneRepository extends JpaRepository<CustomerPhone, Long> {
    @Query("SELECT c.phone FROM customer c WHERE c.phone = :phone")
    Optional<CustomerPhone> findPhoneByNumber(@Param("phone") String phone);
}
