package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Customer;
import nl.hsleiden.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CustomerController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/api/customers")
    public Collection<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/api/customers/{customerId}")
    public Optional<Customer> getSpecifiedCustomer(@PathVariable Long customerId) {
        LOGGER.info("Fetching customer with id: "  + customerId);
        return customerRepository.findById(customerId);
    }

    @PostMapping("/api/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        LOGGER.info("Creating new customer...");
        return customerRepository.save(customer);
    }

    @PutMapping("/api/customers/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer updatedCustomer) {
        LOGGER.info("Updating customer with id: " + customerId);
        return customerRepository.findById(customerId).map(customer -> {
            customer.setAddress(updatedCustomer.getAddress());
            customer.setCity(updatedCustomer.getCity());
            customer.setCountry(updatedCustomer.getCountry());
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setInfix(updatedCustomer.getInfix());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setTitle(updatedCustomer.getTitle());
            customer.setZipcode(updatedCustomer.getZipcode());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }

    @DeleteMapping("/api/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        LOGGER.info("Deleting customer with id: " + customerId);
        return customerRepository.findById(customerId).map(customer -> {
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id" + customerId));
    }
}
