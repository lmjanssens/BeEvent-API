package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CateringOrder;
import nl.hsleiden.repository.CateringOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CateringOrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(CateringOrderController.class);

    @Autowired
    private CateringOrderRepository cateringOrderRepository;

    @GetMapping("/api/cateringorder")
    public Collection<CateringOrder> getCateringOrders() { return cateringOrderRepository.findAll(); }

    @GetMapping("/api/cateringorder/{cateringorderid}")
    public Optional<CateringOrder> getSpecifiedCateringOrder(@PathVariable Long cateringOrderId) {
        LOGGER.info("Fetching catering order with id:" + cateringOrderId);
        return cateringOrderRepository.findById(cateringOrderId);
    }

    @PostMapping("/api/cateringorder")
    public CateringOrder createCateringOrder(@Valid @RequestBody CateringOrder cateringOrder) {
        LOGGER.info("Creating new catering order...");
        return cateringOrderRepository.save(cateringOrder);
    }

    @PutMapping("/api/cateringorder/{cateringOrderId}")
    public CateringOrder updateCateringOrder(@PathVariable Long cateringOrderId,
                                             @Valid @RequestBody CateringOrder updatedCateringOrder) {
        LOGGER.info("Updating catering with id: " + cateringOrderId);
        return cateringOrderRepository.findById(cateringOrderId).map(cateringOrder -> {
            cateringOrder.setContactCateringDefinite(updatedCateringOrder.getContactCateringDefinite());
            cateringOrder.setContactCateringOption(updatedCateringOrder.getContactCateringOption());
            cateringOrder.setContactCateringSent(updatedCateringOrder.getContactCateringSent());
            cateringOrder.setDateCateringDefinite(updatedCateringOrder.getDateCateringDefinite());
            cateringOrder.setDateCateringOptions(updatedCateringOrder.getDateCateringOptions());
            cateringOrder.setDateCateringSent(updatedCateringOrder.getDateCateringSent());
            cateringOrder.setCateringId(updatedCateringOrder.getCateringId());
            cateringOrder.setNote(updatedCateringOrder.getNote());
            cateringOrder.setOrderId(updatedCateringOrder.getOrderId());
            return cateringOrderRepository.save(cateringOrder);
        }).orElseThrow(() -> new ResourceNotFoundException("Catering order not found with id: " + cateringOrderId));
    }

    @DeleteMapping("/api/cateringorder/{cateringOrderId}")
    public ResponseEntity<?> deleteCateringOrder(@PathVariable Long cateringOrderId) {
        LOGGER.info("Deleting catering with id: " + cateringOrderId);
        return cateringOrderRepository.findById(cateringOrderId).map(cateringOrder -> {
            cateringOrderRepository.delete(cateringOrder);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Catering order not found with id: " + cateringOrderId));
    }
}
