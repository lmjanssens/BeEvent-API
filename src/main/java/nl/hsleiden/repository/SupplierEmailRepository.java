package nl.hsleiden.repository;

import nl.hsleiden.model.SupplierEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierEmailRepository extends JpaRepository<SupplierEmail, Long> {
    List<SupplierEmail> findSupplierById(Long supplierID);

    @Override
    List<SupplierEmail> findAll();
}
