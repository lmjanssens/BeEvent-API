package nl.hsleiden.mock;

import nl.hsleiden.model.Customer;
import nl.hsleiden.model.CustomerEmail;
import nl.hsleiden.model.CustomerPhone;

import java.util.ArrayList;
import java.util.HashSet;

public class MockCustomer extends Customer {

    public MockCustomer() {
        this.setId(1L);
        this.setTitle("mevrouw");
        this.setFirstName("Nina");
        this.setInfix("van der");
        this.setLastName("Hulde");
        this.setAddress("Bloemenlaan 57");
        this.setZipcode("9284WL");
        this.setCountry("Nederland");
        this.setCity("Nijmegen");
        this.setGender('v');

        HashSet<CustomerEmail> customerEmails = new HashSet<>();
        customerEmails.add(new MockCustomerEmail());

        HashSet<CustomerPhone> customerPhones = new HashSet<>();
        customerPhones.add(new MockCustomerPhone());

        this.setEmails(customerEmails);
        this.setPhones(customerPhones);
    }
}
