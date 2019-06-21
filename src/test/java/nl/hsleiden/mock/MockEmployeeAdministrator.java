package nl.hsleiden.mock;

import nl.hsleiden.model.Employee;
import nl.hsleiden.model.EmployeeEmail;
import nl.hsleiden.model.EmployeePhone;

import javax.persistence.Entity;
import java.util.HashSet;

@Entity
public class MockEmployeeAdministrator extends Employee {

    public MockEmployeeAdministrator() {
        this.setFirstName("Arthur");
        this.setInfix(null);
        this.setLastName("Bol");

        HashSet<EmployeeEmail> employeeEmails = new HashSet<>();
        employeeEmails.add(new MockEmployeeEmail());

        HashSet<EmployeePhone> employeePhones = new HashSet<>();
        employeePhones.add(new MockEmployeePhone());

        this.setEmails(employeeEmails);
        this.setPhoneNumbers(employeePhones);

        this.setUser(new MockUserAdministrator());
    }
}
