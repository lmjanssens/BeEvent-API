package nl.hsleiden.repository;

import nl.hsleiden.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findSupplierById(Long supplierID);

    @Override
    List<Supplier> findAll();
}
