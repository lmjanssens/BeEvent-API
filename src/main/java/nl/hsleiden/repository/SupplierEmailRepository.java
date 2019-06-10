package nl.hsleiden.repository;

import nl.hsleiden.model.SupplierEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierEmailRepository extends JpaRepository<SupplierEmail, Long> {
    List<SupplierEmail> findSupplierById(Long supplierID);

    @Override
    List<SupplierEmail> findAll();
}
