package nl.hsleiden.mock;

import nl.hsleiden.model.Employee;
import nl.hsleiden.model.EmployeeEmail;
import nl.hsleiden.model.EmployeePhone;

import javax.persistence.Entity;
import java.util.HashSet;

@Entity
public class MockEmployee extends Employee {

    public MockEmployee() {
        this.setFirstName("Henk");
        this.setInfix("van");
        this.setLastName("Dijk");

        HashSet<EmployeeEmail> employeeEmails = new HashSet<>();
        employeeEmails.add(new MockEmployeeEmail());

        HashSet<EmployeePhone> employeePhones = new HashSet<>();
        employeePhones.add(new MockEmployeePhone());

        this.setEmails(employeeEmails);
        this.setPhoneNumbers(employeePhones);

        this.setUser(new MockUserEmployee());
    }
}
