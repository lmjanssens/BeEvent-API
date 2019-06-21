package nl.hsleiden.mock;

import nl.hsleiden.model.Instructor;

import javax.persistence.Entity;
import java.util.HashSet;

@Entity
public class MockInstructor extends Instructor {

    public MockInstructor() {
        this.setFirstName("Liselot");
        this.setInfix(null);
        this.setLastName("Rijstenbil");

        this.setEmail("instructor@example.com");
        this.setPhoneNumber("0612345677");

        this.setUser(new MockUserInstructor());
    }
}
