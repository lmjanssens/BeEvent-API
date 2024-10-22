package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CustomerOrder;
import nl.hsleiden.repository.CustomerOrderRepository;
import nl.hsleiden.repository.CustomerRepository;
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
public class CustomerOrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/api/customerOrder")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<CustomerOrder> getCustomerOrder() { return customerOrderRepository.findAll(); }

    @GetMapping("/api/customerOrder/{customerOrderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<CustomerOrder> getSpecifiedCustomerOrder(@PathVariable Long customerOrderId) {
        LOGGER.info("Fetching customer order with id: " + customerOrderId);
        return customerOrderRepository.findById(customerOrderId);
    }

    @PostMapping("/api/customerOrder/{orderId}/{customerId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public CustomerOrder createCustomerOrder(@PathVariable Long orderId, @PathVariable Long customerId,@Valid @RequestBody CustomerOrder customerOrder) {
        LOGGER.info("Creating new customer order...");
        return orderRepository.findById(orderId).map(order -> {
            return customerRepository.findById(customerId).map(customer -> {
                customerOrder.setOrder(order);
                customerOrder.setCustomer(customer);
                return customerOrderRepository.save(customerOrder);
            }).orElseThrow(() -> new ResourceNotFoundException("No customer found of id " + customerId));
        }).orElseThrow(() -> new ResourceNotFoundException("No order object found of id " + orderId));
    }

    @PutMapping("/api/customerOrder/{customerOrderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public CustomerOrder updateCustomerOrder(@PathVariable Long customerOrderId,
                                             @Valid @RequestBody CustomerOrder updatedCustomerOrder) {
        LOGGER.info("Updating customer order with id: " + customerOrderId);
        return customerOrderRepository.findById(customerOrderId).map(customerOrder -> {
//            customerOrder.setCustomerId(updatedCustomerOrder.getCustomerId());
//            customerOrder.setOrderId(updatedCustomerOrder.getOrderId());
            return customerOrderRepository.save(customerOrder);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer order not found with id: " + customerOrderId));
    }

    @DeleteMapping("/api/customerOrder/{customerOrderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteCustomerOrder(@PathVariable Long customerOrderId) {
        LOGGER.info("Deleting customer order with id: " + customerOrderId);
        return customerOrderRepository.findById(customerOrderId).map(customerOrder -> {
            customerOrderRepository.delete(customerOrder);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Custommer order not found with id: " + customerOrderId));
    }
}
