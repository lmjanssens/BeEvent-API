package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeID", columnDefinition = "serial")
    private Long id;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @NotNull
    @Length(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @Length(max = 20)
    @Column(name = "infix")
    private String infix;

    @NotNull
    @Length(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeEmail> emails;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeePhone> phones;
}
