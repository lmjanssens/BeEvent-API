package nl.hsleiden.mock;

import nl.hsleiden.model.EmployeePhone;

import javax.persistence.Entity;

@Entity
public class MockEmployeePhone extends EmployeePhone {

    public MockEmployeePhone() {
        this.setPhoneNumber("06" + Long.toString(System.currentTimeMillis()).substring(0, 6));
    }
}
