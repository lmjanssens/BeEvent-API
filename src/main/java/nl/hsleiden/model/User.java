package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", columnDefinition = "serial")
    private Long id;

    @NotNull
    @Length(min = 4, max = 24)
    @Column(name = "username")
    @JsonProperty("username")
    @JsonView(View.Public.class)
    private String username;

    @NotNull
    @Column(name = "password")
    @JsonProperty("password")
    @JsonView(View.Private.class)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserAction> actions;
}
