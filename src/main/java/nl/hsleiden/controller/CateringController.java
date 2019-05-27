package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Catering;
import nl.hsleiden.repository.CateringRepository;
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
public class CateringController {

    private final Logger LOGGER = LoggerFactory.getLogger(CateringController.class);

    @Autowired
    private CateringRepository cateringRepo;

    @Autowired
    private SupplierRepository supplierRepo;

    @GetMapping("/api/caterings")
    private Collection<Catering> getCaterings() { return this.cateringRepo.findAll(); }

    @GetMapping("/api/caterings/{cateringId}")
    public Optional<Catering> getSpecificCatering(@PathVariable Long cateringId) {
        LOGGER.info("Fetching catering of id " + cateringId);
        return cateringRepo.findById(cateringId);
    }

    @PostMapping("/api/caterings/{supplierId}")
    private Catering createCatering(@PathVariable Long supplierId, @Valid @RequestBody Catering catering) {
        LOGGER.info("Creating catering object");

        return this.supplierRepo.findById(supplierId).map(supplier -> {
            catering.setSupplier(supplier);
            return cateringRepo.save(catering);
        }).orElseThrow(() -> new ResourceNotFoundException("No supplier found of id " + supplierId));
    }

    @PutMapping("/api/caterings/{id}")
    public Catering updateCatering(@PathVariable Long id, @RequestBody Catering updatedCatering) {

        LOGGER.info("Updating catering object of id " + id);
        return cateringRepo.findById(id).map(catering -> {

            catering.setCateringName(updatedCatering.getCateringName());
            catering.setContactPerson(updatedCatering.getContactPerson());
            catering.setZipcode(updatedCatering.getZipcode());
            catering.setAddress(updatedCatering.getAddress());
            catering.setCity(updatedCatering.getCity());
            catering.setPhone(updatedCatering.getPhone());
            catering.setCateringPrice(updatedCatering.getCateringPrice());
            catering.setNote(updatedCatering.getNote());

            return cateringRepo.save(catering);

        }).orElseThrow(() -> new ResourceNotFoundException("No catering object found of id " + id));
    }

    @DeleteMapping("/api/caterings/{id}")
    public ResponseEntity<?> deleteCatering(@PathVariable Long id) {

        LOGGER.info("Deleting catering object of id " + id);
        return cateringRepo.findById(id).map(catering -> {
            cateringRepo.delete(catering);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No cateringobject found of id " + id));
    }

}
