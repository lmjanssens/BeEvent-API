package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supplier_address")
public class SupplierAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplieraddressid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierid", nullable = false)
    @JsonBackReference("addressSupplierRef")
    private Supplier supplier;

    @NotNull
    @Length(max = 255)
    @Column(name = "address")
    @JsonView(View.Public.class)
    private String address;

    @NotNull
    @Length(max = 255)
    @Column(name = "city")
    @JsonView(View.Public.class)
    private String city;

    @NotNull
    @Length(max = 255)
    @Column(name = "zipcode")
    @JsonView(View.Public.class)
    private String zipcode;

    public SupplierAddress(Supplier supplier,
                           @NotNull @Length(max = 255) String address,
                           @NotNull @Length(max = 255) String city,
                           @NotNull @Length(max = 255) String zipcode) {

        this.supplier = supplier;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
    }

    public SupplierAddress() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object obj) {
        SupplierAddress supplierAddress = (SupplierAddress) obj;
        return
                this.address.equals(supplierAddress.getAddress()) &&
                        this.zipcode.equals(supplierAddress.getZipcode()) &&
                        this.city.equals(supplierAddress.getCity()) &&
                        this.supplier.getId() == supplierAddress.getSupplier().getId();
    }
}
