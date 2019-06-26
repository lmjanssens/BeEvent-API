package nl.hsleiden.mock;

import nl.hsleiden.model.Supplier;
import nl.hsleiden.model.SupplierAddress;
import nl.hsleiden.model.SupplierEmail;
import nl.hsleiden.model.SupplierPhone;

import javax.persistence.Entity;
import java.util.HashSet;

@Entity
public class MockSupplierAddress extends SupplierAddress {

    public MockSupplierAddress() {
        this.setAddress("Birkholm 141");
        this.setZipcode("1234AB");
        this.setCity("Alphen aan den Rijn");
    }
}
