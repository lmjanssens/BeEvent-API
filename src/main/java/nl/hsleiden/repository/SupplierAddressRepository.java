package nl.hsleiden.repository;

import nl.hsleiden.model.SupplierAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierAddressRepository extends JpaRepository<SupplierAddress, Long> {
    @Override
    List<SupplierAddress> findAll();
}
