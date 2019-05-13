package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Catering;
import nl.hsleiden.repository.CateringRepository;
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
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CateringRepository cateringRepository;

    @GetMapping("/api/catering")
    public Collection<Catering> getCatering() { return cateringRepository.findAll(); }

    @GetMapping("/api/catering/{cateringId}")
    public Optional<Catering> getSpecifiedCatering(@PathVariable Long cateringId) {
        LOGGER.info("Fetching catering with id: " + cateringId);
        return cateringRepository.findById(cateringId);
    }

    @PostMapping("/api/catering")
    public Catering createCatering(@Valid @RequestBody Catering catering) {
        LOGGER.info("Creating new catering");
        return cateringRepository.save(catering);
    }

    @PutMapping("/api/catering/{cateringId}")
    public Catering upcateCatering(@PathVariable Long cateringId, @Valid @RequestBody Catering updatedCatering) {
        LOGGER.info("Updating catering with id: " + cateringId);
        return cateringRepository.findById(cateringId).map(catering -> {
            catering.setAddress(updatedCatering.getAddress());
            catering.setCateringPrice(updatedCatering.getCateringPrice());
            catering.setCity(updatedCatering.getCity());
            catering.setContactPerson(updatedCatering.getContactPerson());
            catering.setName(updatedCatering.getName());
            catering.setNote(updatedCatering.getNote());
            catering.setPhone(updatedCatering.getPhone());
            catering.setSupplierId(updatedCatering.getSupplierId());
            catering.setZipcode(updatedCatering.getZipcode());
            return cateringRepository.save(catering);
        }).orElseThrow(() -> new ResourceNotFoundException("Catering not found with id " + cateringId));
    }

    @DeleteMapping("/api/catering/{cateringId}")
    public ResponseEntity<?> deleteCatering(@PathVariable Long cateringId) {
        LOGGER.info("Deleting catering with id: " + cateringId);
        return  cateringRepository.findById(cateringId).map(catering -> {
            cateringRepository.delete(catering);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Catering not found with id: " + cateringId));
    }
}
