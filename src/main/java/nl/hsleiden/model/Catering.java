package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Datamodel class Catering
 * @author Robin Silverio
 */

@Entity
@Table(name = "catering")
public class Catering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringid", columnDefinition = "SERIAL")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierid")
    @JsonView(View.Public.class)
    private Supplier supplier;

    @NotNull
    @Column(name = "catering_name")
    @JsonView(View.Public.class)
    private String cateringName;

    @NotNull
    @Column(name = "contact_person")
    @JsonView(View.Public.class)
    private String contactPerson;

    @NotNull
    @Column(name = "zipcode")
    @JsonView(View.Public.class)
    private String zipcode;

    @NotNull
    @Column(name = "address")
    @JsonView(View.Public.class)
    private String address;

    @NotNull
    @Column(name = "city")
    @JsonView(View.Public.class)
    private String city;

    @NotNull
    @Column(name = "phone")
    @JsonView(View.Public.class)
    private String phone;

    @NotNull
    @Column(name = "catering_price")
    @JsonView(View.Public.class)
    private double cateringPrice;

    @Column(name = "note")
    @JsonView(View.Public.class)
    private String note;

    public Catering(Supplier supplier, String cateringName, String contactPerson, String zipcode, String address, String city, String phone, double cateringPrice, String note) {
        this.supplier = supplier;
        this.cateringName = cateringName;
        this.contactPerson = contactPerson;
        this.zipcode = zipcode;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.cateringPrice = cateringPrice;
        this.note = note;
    }

    public Catering() {}

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

    public String getCateringName() {
        return cateringName;
    }

    public void setCateringName(String cateringName) {
        this.cateringName = cateringName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getCateringPrice() {
        return cateringPrice;
    }

    public void setCateringPrice(double cateringPrice) {
        this.cateringPrice = cateringPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
