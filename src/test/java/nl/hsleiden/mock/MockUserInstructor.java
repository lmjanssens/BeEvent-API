package nl.hsleiden.mock;

import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.persistence.Entity;

@Entity
public class MockUserInstructor extends User {

    private static UserService userService = new UserService();

    public MockUserInstructor() {
        this.setUsername("instructor");
        this.setPassword("password");

        MockUserInstructor.userService.encodePassword(this);
    }
}
