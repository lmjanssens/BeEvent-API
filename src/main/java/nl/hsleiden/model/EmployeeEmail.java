package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee_email")
public class EmployeeEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeEmailID", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Employee employee;

    @NotNull
    @Length(max = 150)
    @Column(name = "email")
    private String email;
}
