package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    @JsonView(View.Public.class)
    private Employee employee;

    @NotNull
    @Length(max = 10)
    @Pattern(regexp="(^[0-9]{10}$)")
    @Column(name = "phonenumber")
    @JsonProperty("phone")
    @JsonView(View.Public.class)
    private String phone;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object object) {
        EmployeePhone phone = (EmployeePhone) object;
        return this.phone.equals(phone.getPhone());
    }
}
