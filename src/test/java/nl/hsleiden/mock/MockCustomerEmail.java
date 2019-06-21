package nl.hsleiden.mock;

import nl.hsleiden.model.CustomerEmail;

import javax.persistence.Entity;

@Entity
public class MockCustomerEmail extends CustomerEmail {

    public MockCustomerEmail() {

        this.setEmail("customer@example.com");
    }
}
