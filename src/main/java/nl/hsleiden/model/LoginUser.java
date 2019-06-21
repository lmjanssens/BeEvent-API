package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginUser {

    @JsonView(View.Public.class)
    private Long uid;

    @JsonView(View.Public.class)
    private String username;

    @JsonView(View.Public.class)
    private String role;

    public LoginUser(Long uid, String username, String role) {
        this.uid = uid;
        this.username = username;
        this.role = role;
    }

    public Long getUid() { return uid; }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return this.role;
    }
}
