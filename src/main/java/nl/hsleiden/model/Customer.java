package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", columnDefinition = "serial")
    private Long id;

    @Length(max = 10)
    @Column(name = "title")
    private String title;

    @NotNull
    @Length(max = 30)
    @Column(name = "first_name")
    private String firstName;

    @Length(max = 15)
    @Column(name = "infix")
    private String infix;

    @NotNull
    @Length(max = 30)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Length(max = 50)
    @Column(name = "address")
    private String address;

    @NotNull
    @Length(max = 10)
    @Column(name = "zipcode")
    private String zipcode;

    @NotNull
    @Length(max = 50)
    @Column(name = "country")
    private String country;

    @NotNull
    @Length(max = 50)
    @Column(name = "city")
    private String city;

    public Customer(Long id, String title, String firstName, String infix, String lastName, String address,
                    String zipcode, String country, String city) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.address = address;
        this.zipcode = zipcode;
        this.country = country;
        this.city = city;
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
