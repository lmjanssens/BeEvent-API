package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CateringOrder;
import nl.hsleiden.model.CateringOrderOption;
import nl.hsleiden.repository.CateringOrderOptionRepository;
import nl.hsleiden.repository.CateringOrderRepository;
import nl.hsleiden.repository.CateringRepository;
import nl.hsleiden.repository.OrderRepository;
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
public class CateringOrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(CateringOrderController.class);

    @Autowired
    private CateringOrderRepository cateringOrderRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CateringRepository cateringRepo;

    @Autowired
    private CateringOrderOptionRepository cateringOrderOptionRepo;

    @GetMapping("/api/cateringorder")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<CateringOrder> getCateringOrders() { return this.cateringOrderRepo.findAll(); }

    @GetMapping("/api/cateringorder/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<CateringOrder> getSpecificCateringOrder(@PathVariable Long id) {
        LOGGER.info("Fetching catering order object of id " + id);
        return cateringOrderRepo.findById(id);
    }

    @PostMapping("/api/cateringorder/{orderId}/{cateringId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public CateringOrder createCateringOrder(@PathVariable Long orderId,
                                             @PathVariable Long cateringId,
                                             @Valid @RequestBody CateringOrder cateringOrder) {
        LOGGER.info("Creating a catering order object");
        return orderRepo.findById(orderId).map(order -> {
            return cateringRepo.findById(cateringId).map(catering -> {
                cateringOrder.setOrder(order);
                cateringOrder.setCatering(catering);
                saveOrderOptions(cateringOrder, cateringOrder.getOptions());
                return cateringOrderRepo.save(cateringOrder);
            }).orElseThrow(() -> new ResourceNotFoundException("No catering object found of id " + cateringId));
        }).orElseThrow(() -> new ResourceNotFoundException("No order object found of id " + orderId));

    }

    @PutMapping("/api/cateringorder/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public CateringOrder updateCateringOrder(@PathVariable Long id,
                                             @Valid @RequestBody CateringOrder updatedCateringOrder) {
        LOGGER.info("Updating catering order object of id " + id);
        return cateringOrderRepo.findById(id).map(cateringOrder -> {

            cateringOrder.setOptions(updatedCateringOrder.getOptions());
            cateringOrder.setDateCateringOptions(updatedCateringOrder.getDateCateringOptions());
            cateringOrder.setDateCateringDefinite(updatedCateringOrder.getDateCateringDefinite());
            cateringOrder.setDateCateringSend(updatedCateringOrder.getDateCateringSend());
            cateringOrder.setContactCateringOption(updatedCateringOrder.getContactCateringOption());
            cateringOrder.setContactCateringDefinite(updatedCateringOrder.getContactCateringDefinite());
            cateringOrder.setContactCateringSend(updatedCateringOrder.getContactCateringSend());
            cateringOrder.setNote(updatedCateringOrder.getNote());

            return cateringOrderRepo.save(cateringOrder);
        }).orElseThrow(() -> new ResourceNotFoundException("No catering order object found of id " + id));
    }

    @DeleteMapping("/api/cateringorder/{id}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteCateringOrder(@PathVariable Long id) {
        LOGGER.info("Deleting a catering order object of id " + id);
        return cateringOrderRepo.findById(id).map(cateringOrder -> {
            cateringOrderRepo.delete(cateringOrder);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No catering order object found of id " + id));
    }

    @PostMapping(("/api/cateringorder/{orderId}/{cateringId}"))
    private void saveOrderOptions(CateringOrder cateringOrder, Collection<CateringOrderOption> toBeSaved) {
        try {
            for (CateringOrderOption cateringOrderOption : toBeSaved) {
                cateringOrderOption.setCateringorder(cateringOrderOption.getCateringorder());
            }
            cateringOrderOptionRepo.saveAll(toBeSaved);

        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save contractsoptions");
        }
    }

    private void deleteContractOptions(Collection<CateringOrderOption> toBeDeleted) {
        try {
            cateringOrderOptionRepo.deleteAll(toBeDeleted);
        } catch (NullPointerException e){
            LOGGER.info("Unable to delete addresses");
        }
    }

}
