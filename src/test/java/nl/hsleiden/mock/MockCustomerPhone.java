package nl.hsleiden.mock;

import nl.hsleiden.model.CustomerPhone;

import javax.persistence.Entity;

@Entity
public class MockCustomerPhone extends CustomerPhone {

    public MockCustomerPhone() {
        this.setPhone("0612345678");
    }
}
