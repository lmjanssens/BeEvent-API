package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "instructor")
public class Instructor {

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

    @NotNull
    @Length(min = 10, max = 20)
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @NotNull
    @Email
    @Length(max = 150)
    @Column(name = "email")
    private String email;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
