package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerPhoneRepository extends JpaRepository<CustomerPhone, Long> {
    @Query(value = "SELECT p.phonenumber " +
            "FROM customer_phone p " +
            "JOIN customer c ON p.customerid = c.customerid" +
            "WHERE p.phone = :phone", nativeQuery = true)
    Optional<CustomerPhone> findPhoneByNumber(@Param("phone") String phone);
}
