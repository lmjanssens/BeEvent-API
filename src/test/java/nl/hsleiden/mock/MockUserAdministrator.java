package nl.hsleiden.mock;

import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.persistence.Entity;

@Entity
public class MockUserAdministrator extends User {

    private static UserService userService = new UserService();

    public MockUserAdministrator() {
        this.setUsername("administrator");
        this.setPassword("password");

        MockUserAdministrator.userService.encodePassword(this);
    }
}
