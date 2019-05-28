package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Supplier;
import nl.hsleiden.model.SupplierContract;
import nl.hsleiden.model.SupplierEmail;
import nl.hsleiden.model.SupplierPhone;
import nl.hsleiden.repository.SupplierContractRepository;
import nl.hsleiden.repository.SupplierEmailRepository;
import nl.hsleiden.repository.SupplierPhoneRepository;
import nl.hsleiden.repository.SupplierRepository;
import nl.hsleiden.service.CollectionDataService;
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
    private final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierContractRepository supplierContractRepository;

    @Autowired
    private SupplierEmailRepository supplierEmailRepository;

    @Autowired
    private SupplierPhoneRepository supplierPhoneRepository;

    private CollectionDataService<SupplierContract> supplierContractCollectionDataService = new CollectionDataService<>();
    private CollectionDataService<SupplierEmail> supplierEmailCollectionDataService = new CollectionDataService<>();
    private CollectionDataService<SupplierPhone> supplierPhoneCollectionDataService = new CollectionDataService<>();

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
        Supplier savedSupplier = supplierRepository.save(supplier);

        saveContracts(savedSupplier, supplier.getContracts());
        saveEmails(savedSupplier, supplier.getEmails());
        savePhones(savedSupplier, supplier.getPhones());

        return supplierRepository.save(supplier);
    }

    @PutMapping("/api/suppliers/{supplierId}")
    public Supplier updateSupplier(@PathVariable Long supplierId, @Valid @RequestBody Supplier updatedSupplier) {
        LOGGER.info("Updating supplier with id: " + supplierId);
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplier.setName(updatedSupplier.getName());

            supplier.setWebsite(updatedSupplier.getWebsite());
            supplier.setImage((updatedSupplier.getImage()));

            supplier.setContactPerson(updatedSupplier.getContactPerson());
            supplier.setSupervisor(updatedSupplier.getSupervisor());

            supplier.setNote(updatedSupplier.getNote());

            Collection<SupplierContract> supplierContractsToBeSaved = supplierContractCollectionDataService.getToBeSaved(supplier.getContracts(), updatedSupplier.getContracts());
            Collection<SupplierContract> supplierContractsToBeDeleted = supplierContractCollectionDataService.getToBeDeleted(supplier.getContracts(), updatedSupplier.getContracts());

            Collection<SupplierEmail> supplierEmailsToBeSaved = supplierEmailCollectionDataService.getToBeSaved(supplier.getEmails(), updatedSupplier.getEmails());
            Collection<SupplierEmail> supplierEmailsToBeDeleted = supplierEmailCollectionDataService.getToBeDeleted(supplier.getEmails(), updatedSupplier.getEmails());

            Collection<SupplierPhone> supplierPhonesToBeSaved = supplierPhoneCollectionDataService.getToBeSaved(supplier.getPhones(), updatedSupplier.getPhones());
            Collection<SupplierPhone> supplierPhonesToBeDeleted = supplierPhoneCollectionDataService.getToBeDeleted(supplier.getPhones(), updatedSupplier.getPhones());

            // TODO
            saveContracts(supplier, supplierContractsToBeSaved);
            deleteContracts(supplierContractsToBeDeleted);

            saveEmails(supplier, supplierEmailsToBeSaved);
            deleteEmails(supplierEmailsToBeDeleted);

            savePhones(supplier, supplierPhonesToBeSaved);
            deletePhones(supplierPhonesToBeDeleted);

            supplier.setContracts(supplierContractCollectionDataService.getDefinitiveCollection(supplier.getContracts(), supplierContractsToBeSaved, supplierContractsToBeDeleted));
            supplier.setEmails(supplierEmailCollectionDataService.getDefinitiveCollection(supplier.getEmails(), supplierEmailsToBeSaved, supplierEmailsToBeDeleted));
            supplier.setPhones(supplierPhoneCollectionDataService.getDefinitiveCollection(supplier.getPhones(), supplierPhonesToBeSaved, supplierPhonesToBeDeleted));

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

    private void saveContracts(Supplier supplier, Collection<SupplierContract> toBeSaved) {
        try {
            for (SupplierContract supplierContract : toBeSaved)
                supplierContract.setSupplier(supplier);

            supplierContractRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save contracts");
        }
    }

    private void deleteContracts(Collection<SupplierContract> toBeDeleted) {
        try {
            supplierContractRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete contracts");
        }
    }

    private void saveEmails(Supplier supplier, Collection<SupplierEmail> toBeSaved) {
        try {
            for (SupplierEmail supplierEmail : toBeSaved)
                supplierEmail.setSupplier(supplier);

            supplierEmailRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save contracts");
        }
    }

    private void deleteEmails(Collection<SupplierEmail> toBeDeleted) {
        try {
            supplierEmailRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete contracts");
        }
    }

    private void savePhones(Supplier supplier, Collection<SupplierPhone> toBeSaved) {
        try {
            for (SupplierPhone supplierPhone : toBeSaved)
                supplierPhone.setSupplier(supplier);

            supplierPhoneRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save contracts");
        }
    }

    private void deletePhones(Collection<SupplierPhone> toBeDeleted) {
        try {
            supplierPhoneRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete contracts");
        }
    }
}
