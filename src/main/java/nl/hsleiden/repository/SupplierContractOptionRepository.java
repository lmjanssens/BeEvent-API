package nl.hsleiden.repository;

import nl.hsleiden.model.CateringOrderOption;
import nl.hsleiden.model.SupplierContract;
import nl.hsleiden.model.SupplierContractOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierContractOptionRepository extends JpaRepository<SupplierContractOption, Long> {

    List<SupplierContractOption> findByContract(SupplierContract contract);

        @Override
        List<SupplierContractOption> findAll();
    }
