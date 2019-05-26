package nl.hsleiden.repository;

import nl.hsleiden.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findSupplierById(Long supplierID);

    @Override
    List<Supplier> findAll();
}
