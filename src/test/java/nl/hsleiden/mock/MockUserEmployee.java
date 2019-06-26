package nl.hsleiden.mock;

import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.persistence.Entity;

@Entity
public class MockUserEmployee extends User {

    private static UserService userService = new UserService();

    public MockUserEmployee() {
        this.setUsername("employee");
        this.setPassword("password");

        MockUserEmployee.userService.encodePassword(this);
    }
}
