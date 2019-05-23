package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Supplier;
import nl.hsleiden.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class SupplierController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/api/suppliers")
    public Collection<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @GetMapping("/api/suppliers/{supplierId}")
    public Optional<Supplier> getSupplier(@PathVariable Long supplierId) {
        LOGGER.info("Fetching supplier by id: " + supplierId);
        return supplierRepository.findById(supplierId);
    }

    @PostMapping("/api/suppliers")
    public Supplier createSupplier(@Valid @RequestBody Supplier supplier) {
        LOGGER.info("Creating supplier.");
        return supplierRepository.save(supplier);
    }

    @PutMapping("/api/suppliers/{supplierId}")
    public Supplier updateSupplier(@PathVariable Long supplierId, @Valid @RequestBody Supplier updatedSupplier) {
        LOGGER.info("Updating supplier with id: " + supplierId);
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplier.setFirstName(updatedSupplier.getFirstName());
            supplier.setInfix(updatedSupplier.getInfix());
            supplier.setLastName(updatedSupplier.getLastName());

            supplier.setEmails(updatedSupplier.getEmails());
            supplier.setPhones(updatedSupplier.getPhones());
            supplier.setWebsite(updatedSupplier.getWebsite());
            supplier.setImage((updatedSupplier.getImage()));

            supplier.setContactPerson(updatedSupplier.getContactPerson());
            supplier.setSupervisor(updatedSupplier.getSupervisor());

            supplier.setContracts(supplier.getContracts());

            supplier.setNote(updatedSupplier.getNote());

            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + supplierId));
    }

    @DeleteMapping("/api/suppliers/{supplierId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierId) {
        LOGGER.info("Deleting supplier with id: " + supplierId);
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplierRepository.delete(supplier);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + supplierId));
    }
}
