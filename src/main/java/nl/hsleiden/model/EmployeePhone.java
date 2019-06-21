package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "employee_phone")
public class EmployeePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeephoneid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employeeid", nullable = false)
    @JsonBackReference("phoneEmployeeRef")
    @JsonView(View.Public.class)
    private Employee employee;

    @NotNull
    @Length(max = 10)
    @Pattern(regexp="(^[0-9]{10}$)")
    @Column(name = "phonenumber")
    @JsonProperty("phone")
    @JsonView(View.Public.class)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object object) {
        EmployeePhone phone = (EmployeePhone) object;
        return this.phoneNumber.equals(phone.getPhoneNumber());
    }
}
