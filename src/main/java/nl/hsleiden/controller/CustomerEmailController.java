package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CustomerEmail;
import nl.hsleiden.repository.CustomerEmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CustomerEmailController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerEmailRepository customerEmailRepository;

    @GetMapping("/api/customeremail")
    public Collection<CustomerEmail> getCustomerEmails() {
        return customerEmailRepository.findAll();
    }

    @GetMapping("/api/customersemail/{email}")
    public Optional<CustomerEmail> getSpecifiedCustomerEmail(@PathVariable String email) {
        LOGGER.info("Fetching email with address: " + email);
        return customerEmailRepository.findEmailByAddress(email);
    }

    @PostMapping("/api/customeremail")
    public CustomerEmail createCustomerEmail(@Valid @RequestBody CustomerEmail customerEmail) {
        LOGGER.info("Creating new customer email...");
        return customerEmailRepository.save(customerEmail);
    }

    @PutMapping("/api/customeremail/{email}")
    public CustomerEmail updateCustomerEmail(@PathVariable String email,
                                             @Valid @RequestBody CustomerEmail updatedCustomerEmail) {
        LOGGER.info("Updating customer email with address: " + email);
        return customerEmailRepository.findEmailByAddress(email).map(customerEmail -> {
            customerEmail.setEmail(updatedCustomerEmail.getEmail());
            return customerEmailRepository.save(customerEmail);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer email not found with address: " + email));
    }

    @DeleteMapping("/api/customeremail/{email}")
    public ResponseEntity<?> deleteCustomerEmail(@PathVariable String email) {
        LOGGER.info("Deleting customer email with address: " + email);
        return customerEmailRepository.findEmailByAddress(email).map(customerEmail -> {
            customerEmailRepository.delete(customerEmail);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Customer email not found with address: " + email));
    }
}
