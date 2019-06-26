package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.*;
import nl.hsleiden.repository.*;
import nl.hsleiden.service.CollectionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import javax.annotation.security.RolesAllowed;
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

    @Autowired
    private SupplierAddressRepository supplierAddressRepo;

    @Autowired
    private SupplierContractOptionRepository supplierContractOptionRepo;

    private CollectionDataService<SupplierContract> supplierContractCollectionDataService = new CollectionDataService<>();
    private CollectionDataService<SupplierEmail> supplierEmailCollectionDataService = new CollectionDataService<>();
    private CollectionDataService<SupplierPhone> supplierPhoneCollectionDataService = new CollectionDataService<>();
    private CollectionDataService<SupplierAddress> supplierAddressCollectionDataService = new CollectionDataService<>();
    private CollectionDataService<SupplierContractOption> supplierContractOptionCollectionDataService = new CollectionDataService<>();

    @GetMapping("/api/suppliers")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @GetMapping("/api/suppliers/{supplierId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<Supplier> getSupplier(@PathVariable Long supplierId) {
        LOGGER.info("Fetching supplier by id: " + supplierId);
        return supplierRepository.findById(supplierId);
    }

    @PostMapping("/api/suppliers")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Supplier createSupplier(@Valid @RequestBody Supplier supplier) {
        LOGGER.info("Creating supplier.");
        Supplier savedSupplier = supplierRepository.save(supplier);
        Set<SupplierContract> contracts;
        contracts = savedSupplier.getContracts();
        for (SupplierContract contract : contracts) {
            saveContractOptions(contract, contract.getOptions());
        }
        saveContracts(savedSupplier, supplier.getContracts());
        saveEmails(savedSupplier, supplier.getEmails());
        savePhones(savedSupplier, supplier.getPhones());
        saveAddresses(savedSupplier, supplier.getAddresses());

        savedSupplier.setContracts(supplier.getContracts());
        savedSupplier.setEmails(supplier.getEmails());
        savedSupplier.setPhones(supplier.getPhones());
        savedSupplier.setAddresses(supplier.getAddresses());

        return supplierRepository.save(savedSupplier);
    }

    @PutMapping("/api/suppliers/{supplierId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Supplier updateSupplier(@PathVariable Long supplierId, @Valid @RequestBody Supplier updatedSupplier) {
        LOGGER.info("Updating supplier with id: " + supplierId);
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplier.setName(updatedSupplier.getName());

            supplier.setWebsite(updatedSupplier.getWebsite());
            supplier.setImage((updatedSupplier.getImage()));

            supplier.setContactPerson(updatedSupplier.getContactPerson());
            supplier.setSupervisor(updatedSupplier.getSupervisor());

            supplier.setNote(updatedSupplier.getNote());
            Set<SupplierContract> contracts;
            contracts = updatedSupplier.getContracts();

            Collection<SupplierContract> supplierContractsToBeSaved = supplierContractCollectionDataService.getToBeSaved(supplier.getContracts(), updatedSupplier.getContracts());
            Collection<SupplierContract> supplierContractsToBeDeleted = supplierContractCollectionDataService.getToBeDeleted(supplier.getContracts(), updatedSupplier.getContracts());

            for (SupplierContract contract : supplierContractsToBeSaved) {
                for (SupplierContract oldcontract : supplier.getContracts()) {
                    Collection<SupplierContractOption> supplierContractOptionsToBeSaved = supplierContractOptionCollectionDataService.getToBeSaved(oldcontract.getOptions(), contract.getOptions());
                    saveContractOptions(contract ,supplierContractOptionsToBeSaved);
                }
            }

            for (SupplierContract contract : supplierContractsToBeDeleted) {
                for (SupplierContract oldcontract : supplier.getContracts()) {
                    Collection<SupplierContractOption> supplierContractOptionsToBeDeleted = supplierContractOptionCollectionDataService.getToBeDeleted(oldcontract.getOptions(), contract.getOptions());
                    deleteContractOptions(supplierContractOptionsToBeDeleted);
                }
            }

            Collection<SupplierEmail> supplierEmailsToBeSaved = supplierEmailCollectionDataService.getToBeSaved(supplier.getEmails(), updatedSupplier.getEmails());
            Collection<SupplierEmail> supplierEmailsToBeDeleted = supplierEmailCollectionDataService.getToBeDeleted(supplier.getEmails(), updatedSupplier.getEmails());

            Collection<SupplierPhone> supplierPhonesToBeSaved = supplierPhoneCollectionDataService.getToBeSaved(supplier.getPhones(), updatedSupplier.getPhones());
            Collection<SupplierPhone> supplierPhonesToBeDeleted = supplierPhoneCollectionDataService.getToBeDeleted(supplier.getPhones(), updatedSupplier.getPhones());

            Collection<SupplierAddress> supplierAddressesToBeSaved = supplierAddressCollectionDataService.getToBeSaved(supplier.getAddresses(), updatedSupplier.getAddresses());
            Collection<SupplierAddress> supplierAddressesToBeDeleted = supplierAddressCollectionDataService.getToBeDeleted(supplier.getAddresses(), updatedSupplier.getAddresses());

            saveContracts(supplier, supplierContractsToBeSaved);
            deleteContracts(supplierContractsToBeDeleted);

            saveEmails(supplier, supplierEmailsToBeSaved);
            deleteEmails(supplierEmailsToBeDeleted);

            savePhones(supplier, supplierPhonesToBeSaved);
            deletePhones(supplierPhonesToBeDeleted);

            saveAddresses(supplier, supplierAddressesToBeSaved);
            deleteAddresses(supplierAddressesToBeDeleted);

            supplier.setContracts(supplierContractCollectionDataService.getDefinitiveCollection(supplier.getContracts(), supplierContractsToBeSaved, supplierContractsToBeDeleted));
            supplier.setEmails(supplierEmailCollectionDataService.getDefinitiveCollection(supplier.getEmails(), supplierEmailsToBeSaved, supplierEmailsToBeDeleted));
            supplier.setPhones(supplierPhoneCollectionDataService.getDefinitiveCollection(supplier.getPhones(), supplierPhonesToBeSaved, supplierPhonesToBeDeleted));
            supplier.setAddresses(supplierAddressCollectionDataService.getDefinitiveCollection(supplier.getAddresses(), supplierAddressesToBeSaved, supplierAddressesToBeDeleted));

            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + supplierId));
    }

    @DeleteMapping("/api/suppliers/{supplierId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierId) {
        LOGGER.info("Deleting supplier with id: " + supplierId);
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplierRepository.delete(supplier);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + supplierId));
    }


    @DeleteMapping("/api/suppliers/{supplierId}/{contractId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteContract(@PathVariable Long contractId) {
        LOGGER.info("Deleting contract with id: " + contractId);
        return supplierContractRepository.findById(contractId).map(contract -> {
            supplierContractRepository.delete(contract);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + contractId));
    }

    private void saveContracts(Supplier supplier, Collection<SupplierContract> toBeSaved) {
        try {
            for (SupplierContract supplierContract : toBeSaved) {
                supplierContract.setSupplier(supplier);
                supplierContract.setId(null);
            }

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

    /**
     * For saving multiple addresses of one supplier.
     *
     * @param supplier  id of a supplier
     * @param toBeSaved JSON-object obtained ready to be stored in the database
     * @author Robin Silverio
     */
    private void saveAddresses(Supplier supplier, Collection<SupplierAddress> toBeSaved) {
        try {
            for (SupplierAddress address : toBeSaved)
                address.setSupplier(supplier);
            supplierAddressRepo.saveAll(toBeSaved);
        } catch (NullPointerException e) {
            LOGGER.info("Unable to save addresses");
        }
    }

    /**
     * For deleting set of addresses of one supplier
     *
     * @param toBeDeleted JSON-object containing a list of addresses ready to be deleted
     * @author Robin Silverio
     */
    private void deleteAddresses(Collection<SupplierAddress> toBeDeleted) {
        try {
            supplierAddressRepo.deleteAll(toBeDeleted);
        } catch (NullPointerException e) {
            LOGGER.info("Unable to delete addresses");
        }
    }

    private void saveContractOptions(SupplierContract supplierContract, Collection<SupplierContractOption> toBeSaved) {
        try {
            for (SupplierContractOption supplierContractOption : toBeSaved) {
                supplierContractOption.setContract(supplierContract);
            }
            supplierContractOptionRepo.saveAll(toBeSaved);

        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save contractsoptions");
        }
    }

    private void deleteContractOptions(Collection<SupplierContractOption> toBeDeleted) {
        try {
            supplierContractOptionRepo.deleteAll(toBeDeleted);
        } catch (NullPointerException e) {
            LOGGER.info("Unable to delete addresses");
        }
    }
}
