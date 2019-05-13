package nl.hsleiden.repository;

import nl.hsleiden.model.SupplierPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierPhoneRepository extends JpaRepository<SupplierPhone, Long> {
    List<SupplierPhone> findSupplierEmailById(Long supplierID);

    @Override
    List<SupplierPhone> findAll();
}
