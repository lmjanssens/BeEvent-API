package nl.hsleiden.controllers.repositories;

import nl.hsleiden.model.Customer;
import nl.hsleiden.model.CustomerEmail;
import nl.hsleiden.model.CustomerPhone;
import nl.hsleiden.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findCustomerById() {
        Customer customer = new Customer();

        CustomerPhone[] phones = new CustomerPhone[] {new CustomerPhone("06-29292929")};
        Set<CustomerPhone> customerPhones = new HashSet<>(Arrays.asList(phones));

        CustomerEmail[] emails = new CustomerEmail[] {new CustomerEmail("kill@me.nl")};
        Set<CustomerEmail> customerEmails = new HashSet<>(Arrays.asList(emails));

        customer.setPhones(customerPhones);
        customer.setEmails(customerEmails);
        customer.setGender('m');
//        customer
    }
}
