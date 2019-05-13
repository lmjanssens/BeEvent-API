package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee_phone")
public class EmployeePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeePhoneID", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Employee employee;

    @NotNull
    @Length(max = 20)
    @Column(name = "phone")
    private String phone;
}
