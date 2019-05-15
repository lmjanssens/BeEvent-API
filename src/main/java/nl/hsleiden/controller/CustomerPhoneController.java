package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CustomerPhone;
import nl.hsleiden.repository.CustomerPhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class CustomerPhoneController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerPhoneRepository customerPhoneRepository;

    @GetMapping("/api/customerphone")
    public Collection<CustomerPhone> getPhones() { return customerPhoneRepository.findAll(); }

    @GetMapping("/api/customerphone/{number}")
    public Optional<CustomerPhone> getSpecifiedCustomerPhone(@PathVariable String phone) {
        LOGGER.info("Fetching phone with number: " + phone);
        return customerPhoneRepository.findPhoneByNumber(phone);
    }

    @PostMapping("/api/customerphone")
    public CustomerPhone createCustomerPhone(@Valid @RequestBody CustomerPhone customerPhone) {
        LOGGER.info("Creating new customer phone...");
        return customerPhoneRepository.save(customerPhone);
    }

    @PutMapping("/api/customerphone/{number}")
    public CustomerPhone updateCustomerPhone(@PathVariable String phone,
                                             @Valid @RequestBody CustomerPhone updatedCustomerPhone) {
        LOGGER.info("Updating customer phone with number: " + phone);
        return customerPhoneRepository.findPhoneByNumber(phone).map(customerPhone -> {
            customerPhone.setPhone(updatedCustomerPhone.getPhone());
            return customerPhoneRepository.save(customerPhone);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer phone not found with number: " + phone));
    }

    @DeleteMapping("/api/customerphone/{phone}")
    public ResponseEntity<?> deleteCustomerPhone(@PathVariable String phone) {
        LOGGER.info("Deleting customer phone with number: " + phone);
        return customerPhoneRepository.findPhoneByNumber(phone).map(customerPhone -> {
            customerPhoneRepository.delete(customerPhone);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Customer phone not found with number: " + phone));
    }
}
