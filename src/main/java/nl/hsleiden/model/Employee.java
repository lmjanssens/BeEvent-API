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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<EmployeeEmail> getEmails() {
        return emails;
    }

    public void setEmails(Set<EmployeeEmail> emails) {
        this.emails = emails;
    }

    public Set<EmployeePhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<EmployeePhone> phones) {
        this.phones = phones;
    }
}
