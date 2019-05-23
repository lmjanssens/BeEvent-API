package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Supplier_Phone")
public class SupplierPhone {
    @NotNull
    @Column(name = "supplierID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Supplier supplier;

    @NotNull
    @Length(max = 20)
    @Column(name = "phone")
    private String phone;
}
