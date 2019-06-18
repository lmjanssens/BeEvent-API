package nl.hsleiden.mock;

import nl.hsleiden.model.CustomerEmail;

public class MockCustomerEmail extends CustomerEmail {

    public MockCustomerEmail() {
        this.setId(1L);
        this.setEmail("customer@example.com");
    }
}
