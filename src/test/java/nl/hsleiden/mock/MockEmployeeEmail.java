package nl.hsleiden.mock;

import nl.hsleiden.model.EmployeeEmail;

import javax.persistence.Entity;

@Entity
public class MockEmployeeEmail extends EmployeeEmail {

    public MockEmployeeEmail() {
        this.setEmail(System.currentTimeMillis() + "@example.com");
    }

    public MockEmployeeEmail(String email) {
        this.setEmail(email);
    }
}
