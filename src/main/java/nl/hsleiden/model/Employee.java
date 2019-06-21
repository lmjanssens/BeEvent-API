package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.FetchMode;
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
    @JsonProperty("employee_id")
    @JsonView(View.Public.class)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", nullable = false)
    @JsonProperty("user_id")
    @JsonView(View.Public.class)
    private User user;

    @NotNull
    @Length(max = 100)
    @Column(name = "first_name")
    @JsonProperty("first_name")
    @JsonView(View.Public.class)
    private String firstName;

    @Length(max = 20)
    @Column(name = "infix")
    @JsonProperty("infix")
    @JsonView(View.Public.class)
    private String infix;

    @NotNull
    @Length(max = 100)
    @Column(name = "last_name")
    @JsonProperty("last_name")
    @JsonView(View.Public.class)
    private String lastName;

    @Length(max = 250)
    @Column(name = "note")
    @JsonProperty("note")
    @JsonView(View.Public.class)
    private String note;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonProperty("email_addresses")
    @JsonView(View.Public.class)
    private Set<EmployeeEmail> emails;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonProperty("phone_numbers")
    @JsonView(View.Public.class)
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

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

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
