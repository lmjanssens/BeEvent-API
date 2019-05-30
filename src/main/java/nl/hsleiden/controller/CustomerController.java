package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Customer;
import nl.hsleiden.model.CustomerEmail;
import nl.hsleiden.model.CustomerPhone;
import nl.hsleiden.model.Order;
import nl.hsleiden.repository.CustomerEmailRepository;
import nl.hsleiden.repository.CustomerPhoneRepository;
import nl.hsleiden.repository.CustomerRepository;
import nl.hsleiden.repository.OrderRepository;
import nl.hsleiden.service.CollectionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class CustomerController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEmailRepository customerEmailRepository;

    @Autowired
    private CustomerPhoneRepository customerPhoneRepository;

    @Autowired
    private OrderRepository orderRepository;

    CollectionDataService<CustomerEmail> emailCollectionDataService = new CollectionDataService<>();
    CollectionDataService<CustomerPhone> phoneCollectionDataService = new CollectionDataService<>();

    @GetMapping("/api/customers")
    public Collection<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/api/customers/{customerId}")
    public Optional<Customer> getSpecifiedCustomer(@PathVariable Long customerId) {
        LOGGER.info("Fetching customer with id: " + customerId);
        return customerRepository.findById(customerId);
    }

    @PostMapping("/api/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        LOGGER.info("Creating new customer...");
        Customer savedCustomer = customerRepository.save(customer);

        Collection<CustomerEmail> customerEmails = customer.getEmails();
        Collection<CustomerPhone> customerPhones = customer.getPhones();

        this.saveEmailAddresses(savedCustomer, customerEmails);
        this.savePhoneNumbers(savedCustomer, customerPhones);

        return savedCustomer;
    }

    @PutMapping("/api/customers/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer updatedCustomer) {
        LOGGER.info("Updating customer with id: " + customerId);

        return customerRepository.findById(customerId).map(customer -> {
            customer.setAddress(updatedCustomer.getAddress());
            customer.setCity(updatedCustomer.getCity());
            customer.setCountry(updatedCustomer.getCountry());
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setGender(updatedCustomer.getGender());
            customer.setInfix(updatedCustomer.getInfix());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setTitle(updatedCustomer.getTitle());
            customer.setZipcode(updatedCustomer.getZipcode());
            
            Collection<CustomerEmail> emailsToSave = emailCollectionDataService.getToBeSaved(customer.getEmails(), updatedCustomer.getEmails());
            Collection<CustomerEmail> emailsToDelete = emailCollectionDataService.getToBeDeleted(customer.getEmails(), updatedCustomer.getEmails());

            Collection<CustomerPhone> phonesToSave = phoneCollectionDataService.getToBeSaved(customer.getPhones(), updatedCustomer.getPhones());
            Collection<CustomerPhone> phonesToDelete = phoneCollectionDataService.getToBeDeleted(customer.getPhones(), updatedCustomer.getPhones());

            saveEmailAddresses(customer, emailsToSave);
            deleteEmailAddresses(emailsToDelete);

            savePhoneNumbers(customer, phonesToSave);
            deletePhoneNumbers(phonesToDelete);

            customer.setEmails(
                    emailCollectionDataService.getDefinitiveCollection(customer.getEmails(), emailsToSave, emailsToDelete)
            );
            customer.setPhones(
                    phoneCollectionDataService.getDefinitiveCollection(customer.getPhones(), phonesToSave, phonesToDelete)
            );

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

    private void saveEmailAddresses(Customer customer, Collection<CustomerEmail> toBeSaved) {
        LOGGER.info("Saving email addresses...");
        try {
            for (CustomerEmail email : toBeSaved)
                email.setCustomer(customer);

            customerEmailRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save e-mail addresses");
        }
    }

    private void deleteEmailAddresses(Collection<CustomerEmail> toBeDeleted) {
        LOGGER.info("Deleting email addresses...");
        try {
            customerEmailRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete e-mail addresses");
        }
    }

    private void savePhoneNumbers(Customer customer, Collection<CustomerPhone> toBeSaved) {
        LOGGER.info("Saving phone numbers...");
        try {
            for (CustomerPhone phone : toBeSaved)
                phone.setCustomer(customer);

            customerPhoneRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save phone numbers");
        }
    }

    private void deletePhoneNumbers(Collection<CustomerPhone> toBeDeleted) {
        LOGGER.info("Deleting phone numbers...");
        try {
            customerPhoneRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete phone numbers");
        }
    }
}
