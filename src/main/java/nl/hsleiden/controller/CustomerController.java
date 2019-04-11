package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Customer;
import nl.hsleiden.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @GetMapping("/customers/{customerId}")
    public Optional<Customer> getSpecifiedCustomer(@PathVariable  Long customerId) {
        return customerRepository.findById(customerId);
    }

    @PostMapping("/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("/customers/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer updatedCustomer) {
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

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        return customerRepository.findById(customerId).map(customer -> {
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id" + customerId));
    }
}
