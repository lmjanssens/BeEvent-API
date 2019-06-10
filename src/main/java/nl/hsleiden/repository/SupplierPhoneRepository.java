package nl.hsleiden.repository;

import nl.hsleiden.model.SupplierPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierPhoneRepository extends JpaRepository<SupplierPhone, Long> {
    List<SupplierPhone> findSupplierEmailById(Long supplierID);

    @Override
    List<SupplierPhone> findAll();
}
