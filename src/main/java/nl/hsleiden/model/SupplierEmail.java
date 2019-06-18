package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Supplier_Email")
public class SupplierEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplieremailid")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    @JsonBackReference("emailSupplierRef")
    private Supplier supplier;

    @NotNull
    @Length(max = 200)
    @Column(name = "email")
    @JsonProperty("email")
    @JsonView(View.Public.class)
    private String email;

    public SupplierEmail() {

    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        SupplierEmail supplierEmail = (SupplierEmail) object;
        return this.email.equals(supplierEmail.getEmail());
    }
}
