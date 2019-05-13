package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", columnDefinition = "serial")
    private Long id;

    @NotNull
    @Length(min = 4, max = 24)
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserAction> actions;
}
