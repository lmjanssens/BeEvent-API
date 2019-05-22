package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee_email")
public class EmployeeEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeemailid", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employeeid", nullable = false)
    @JsonIgnore
    private Employee employee;

    @NotNull
    @Email
    @Length(max = 150)
    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        EmployeeEmail email = (EmployeeEmail) object;
        return this.email.equals(email.getEmail());
    }
}
