package nl.hsleiden.repository;

import nl.hsleiden.model.CustomerPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerPhoneRepository extends JpaRepository<CustomerPhone, Long> {
    @Query(value = "SELECT p.phonenumber " +
            "FROM customer_phone p " +
            "JOIN customer c ON p.customerid = c.customerid" +
            "WHERE p.phone = :phone", nativeQuery = true)
    Optional<CustomerPhone> findPhoneByNumber(@Param("phone") String phone);

    List<CustomerPhone> findCustomerPhoneByCustomerId(Long customerPhoneId);

    @Override
    List<CustomerPhone> findAll();
}
