package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CateringImage;
import nl.hsleiden.repository.CateringImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CateringImageController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CateringImageRepository cateringImageRepository;

    @GetMapping("/api/cateringimage")
    public Collection<CateringImage> getCateringImages() { return cateringImageRepository.findAll(); }

    @GetMapping("/api/cateringimage/{cateringId}")
    public Optional<CateringImage> getSpecifiedCateringImage(@PathVariable Long cateringId) {
        LOGGER.info("Fetching catering image with id: " + cateringId);
        return cateringImageRepository.findById(cateringId);
    }

    @PostMapping("/api/cateringimage")
    public CateringImage createCateringImage(@Valid @RequestBody CateringImage cateringImage) {
        LOGGER.info("Creating new catering");
        return cateringImageRepository.save(cateringImage);
    }

    @PutMapping("/api/cateringimage/{cateringId}")
    public CateringImage updateCateringImage(@PathVariable Long cateringId,
                                             @Valid @RequestBody CateringImage updatedCateringImage) {
        LOGGER.info("Updating catering image with id: " + cateringId);
        return cateringImageRepository.findById(cateringId).map(cateringImage -> {
            cateringImage.setContractId(updatedCateringImage.getContractId());
            cateringImage.setImage(updatedCateringImage.getImage());
            return cateringImageRepository.save(cateringImage);
        }).orElseThrow(() -> new ResourceNotFoundException("Catering image not found with id " + cateringId));
    }

    @DeleteMapping("/api/cateringimage/{cateringId}")
    public ResponseEntity<?> deleteCateringImage(@PathVariable Long cateringId) {
        LOGGER.info("Deleting catering image with id: " + cateringId);
        return cateringImageRepository.findById(cateringId).map(cateringImage -> {
            cateringImageRepository.delete(cateringImage);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Catering image not found with id: " + cateringId));
    }
}
