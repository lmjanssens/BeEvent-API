package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Supplier_Email")
public class SupplierEmail {
    @NotNull
    @Column(name = "supplierID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Supplier supplier;

    @NotNull
    @Length(max = 200)
    @Column(name = "email")
    private String email;
}
