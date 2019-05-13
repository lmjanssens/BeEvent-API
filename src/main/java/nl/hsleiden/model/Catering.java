package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "catering")
public class Catering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringid", columnDefinition = "serial")
    private Long id;

    @Column(name = "supplierid")
    private Long supplierId;

    @Column(name = "name")
    private String name;

    @Column(name = "contactperson")
    private String contactPerson;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "cateringprice")
    private int cateringPrice;

    @Column(name = "note")
    private String note;

    public Catering(Long supplierId, String name, String contactPerson, String zipcode, String address,
                    String city, String phone, int cateringPrice, String note) {
        this.supplierId = supplierId;
        this.name = name;
        this.contactPerson = contactPerson;
        this.zipcode = zipcode;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.cateringPrice = cateringPrice;
        this.note = note;
    }

    public Catering() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getCateringPrice() {
        return cateringPrice;
    }

    public void setCateringPrice(int cateringPrice) {
        this.cateringPrice = cateringPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
