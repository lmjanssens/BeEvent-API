package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CustomerOrder;
import nl.hsleiden.repository.CustomerOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CustomerOrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @GetMapping("/api/customerOrder")
    public Collection<CustomerOrder> getCustomerOrder() { return customerOrderRepository.findAll(); }

    @GetMapping("/api/customerOrder/{customerOrderId}")
    public Optional<CustomerOrder> getSpecifiedCustomerOrder(@PathVariable Long customerOrderId) {
        LOGGER.info("Fetching customer order with id: " + customerOrderId);
        return customerOrderRepository.findById(customerOrderId);
    }

    @PostMapping("/api/customerOrder")
    public CustomerOrder createCustomerOrder(@Valid @RequestBody CustomerOrder customerOrder) {
        LOGGER.info("Creating new customer order...");
        return customerOrderRepository.save(customerOrder);
    }

    @PutMapping("/api/customerOrder/{customerOrderId}")
    public CustomerOrder updateCustomerOrder(@PathVariable Long customerOrderId,
                                             @Valid @RequestBody CustomerOrder updatedCustomerOrder) {
        LOGGER.info("Updating customer order with id: " + customerOrderId);
        return customerOrderRepository.findById(customerOrderId).map(customerOrder -> {
            customerOrder.setCustomerId(updatedCustomerOrder.getCustomerId());
            customerOrder.setOrderId(updatedCustomerOrder.getOrderId());
            return customerOrderRepository.save(customerOrder);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer order not found with id: " + customerOrderId));
    }

    @DeleteMapping("/api/customerOrder/{customerOrderId}")
    public ResponseEntity<?> deleteCustomerOrder(@PathVariable Long customerOrderId) {
        LOGGER.info("Deleting customer order with id: " + customerOrderId);
        return customerOrderRepository.findById(customerOrderId).map(customerOrder -> {
            customerOrderRepository.delete(customerOrder);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Custommer order not found with id: " + customerOrderId));
    }
}
