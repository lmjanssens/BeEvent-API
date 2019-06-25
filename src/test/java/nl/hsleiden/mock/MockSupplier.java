package nl.hsleiden.mock;

import nl.hsleiden.model.Supplier;
import nl.hsleiden.model.SupplierAddress;
import nl.hsleiden.model.SupplierEmail;
import nl.hsleiden.model.SupplierPhone;

import javax.persistence.Entity;
import java.util.HashSet;

@Entity
public class MockSupplier extends Supplier {

    public MockSupplier() {
        this.setName("Bakkerij Bart");

        this.setContactPerson("Bart Bakker");
        this.setSupervisor("Jan Allemans");

        this.setWebsite("https://bakkerijbart.nl");

        this.setNote(null);
        this.setImage("leverancier.jpg");


        HashSet<SupplierEmail> supplierEmails = new HashSet<>();
        supplierEmails.add(new MockSupplierEmail());

        HashSet<SupplierPhone> supplierPhones = new HashSet<>();
        supplierPhones.add(new MockSupplierPhone());

        HashSet<SupplierAddress> supplierAddresses = new HashSet<>();
        supplierAddresses.add(new MockSupplierAddress());

        this.setEmails(supplierEmails);
        this.setPhones(supplierPhones);
        this.setAddresses(supplierAddresses);
    }
}
