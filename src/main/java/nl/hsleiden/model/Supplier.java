package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierID", columnDefinition = "serial")
    private Long id;

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
    @Column(name = "contact_person")
    private String contactPerson;

    @NotNull
    @Column(name = "contact_person")
    private String supervisor;

    @Length(max = 150)
    @Column(name = "website")
    private String website;

    @Column(name = "note")
    private String note;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierEmail> emails;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierPhone> phones;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierContract> contracts;
}
