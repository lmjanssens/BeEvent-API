package nl.hsleiden.controller;

import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CateringImage;
import nl.hsleiden.repository.CateringImageRepository;
import nl.hsleiden.repository.CateringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CateringImageController {

    private final Logger LOGGER = LoggerFactory.getLogger(CateringImageController.class);

    @Autowired
    private CateringImageRepository cateringImageRepo;

    @Autowired
    private CateringRepository cateringRepo;

    @GetMapping("/api/cateringimages")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Collection<CateringImage> getCateringImages() { return this.cateringImageRepo.findAll(); }

    @GetMapping("/api/cateringimages/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Optional<CateringImage> getSpecificCateringImage(@PathVariable Long id) {
        LOGGER.info("Fetching cateringimage object of id " + id);
        return cateringImageRepo.findById(id);
    }

    @PostMapping("/api/cateringimages/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public CateringImage createCateringImage(@PathVariable Long id, @Valid @RequestBody CateringImage cateringImage) {
        LOGGER.info("Creating a new catering image");
        return cateringRepo.findById(id).map(catering -> {
            cateringImage.setCatering(catering);
            return cateringImageRepo.save(cateringImage);
        }).orElseThrow(() -> new ResourceNotFoundException("No catering object found of id " + id));
    }

    @PutMapping("/api/cateringimages/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public CateringImage updateCateringImage(@PathVariable Long id, @Valid @RequestBody CateringImage updatedCateringImage) {
        LOGGER.info("Updating catering image object of id " + id);
        return cateringImageRepo.findById(id).map(cateringImage -> {
            cateringImage.setImage(updatedCateringImage.getImage());
            return cateringImageRepo.save(cateringImage);
        }).orElseThrow(() -> new ResourceNotFoundException("No catering image object found of id " + id));
    }

    @DeleteMapping("/api/cateringimages/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteCateringImage(@PathVariable Long id) {
        LOGGER.info("Deleting cateringobject of id " + id);
        return cateringImageRepo.findById(id).map(cateringImage -> {
            cateringImageRepo.delete(cateringImage);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No cateringimage object found of id " + id));
    }

}
