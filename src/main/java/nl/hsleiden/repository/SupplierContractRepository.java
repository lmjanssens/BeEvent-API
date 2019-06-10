package nl.hsleiden.repository;

import nl.hsleiden.model.SupplierContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierContractRepository extends JpaRepository<SupplierContract, Long> {
    List<SupplierContract> findSupplierById(Long supplierID);

    @Override
    List<SupplierContract> findAll();
}
