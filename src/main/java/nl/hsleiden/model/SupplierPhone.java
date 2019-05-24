package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Supplier_Phone")
public class SupplierPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierphoneid")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierid", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    @NotNull
    @Length(max = 20)
    @Column(name = "phonenumber")
    @JsonProperty("phone")
    private String phone;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object object) {
        SupplierPhone supplierPhone = (SupplierPhone) object;
        return this.phone.equals(supplierPhone.getPhone());
    }
}
