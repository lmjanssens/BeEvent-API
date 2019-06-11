package nl.hsleiden.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginUser {

    private String username;
    private String role;

    public LoginUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return this.role;
    }
}
