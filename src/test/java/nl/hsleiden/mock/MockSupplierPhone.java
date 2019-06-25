package nl.hsleiden.mock;

import nl.hsleiden.model.SupplierPhone;

import javax.persistence.Entity;

@Entity
public class MockSupplierPhone extends SupplierPhone {

    public MockSupplierPhone() {
        this.setPhone("0612345678");
    }
}
