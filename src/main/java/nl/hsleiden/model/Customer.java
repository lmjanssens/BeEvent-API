package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", columnDefinition = "serial")
    @JsonProperty("customerId")
    private Long id;

    @Length(max = 10)
    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @NotNull
    @Length(max = 30)
    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Length(max = 15)
    @Column(name = "infix")
    @JsonProperty("infix")
    private String infix;

    @NotNull
    @Length(max = 30)
    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    @Length(max = 50)
    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @NotNull
    @Length(max = 10)
    @Column(name = "zipcode")
    @JsonProperty("zipcode")
    private String zipcode;

    @NotNull
    @Length(max = 50)
    @Column(name = "country")
    @JsonProperty("country")
    private String country;

    @NotNull
    @Column(name = "gender")
    @JsonProperty("gender")
    private char gender;

    @NotNull
    @Length(max = 50)
    @Column(name = "city")
    @JsonProperty("city")
    private String city;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonProperty("email_addresses")
    private Set<CustomerEmail> emails;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonProperty("customer_orders")
    private Set<CustomerOrder> customerOrders;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonProperty("phone_numbers")
    private Set<CustomerPhone> phones;

    @OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    @JsonProperty("orders")
    private Set<Order> orders;

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

    public Set<CustomerEmail> getEmails() {
        return emails;
    }

    public void setEmails(Set<CustomerEmail> emails) {
        this.emails = emails;
    }

    public Set<CustomerPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<CustomerPhone> phones) {
        this.phones = phones;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Set<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(Set<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
