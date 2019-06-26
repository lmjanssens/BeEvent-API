package nl.hsleiden.mock;

import nl.hsleiden.model.CustomerEmail;
import nl.hsleiden.model.SupplierEmail;

import javax.persistence.Entity;

@Entity
public class MockSupplierEmail extends SupplierEmail {

    public MockSupplierEmail() {

        this.setEmail("supplier@example.com");
    }
}
