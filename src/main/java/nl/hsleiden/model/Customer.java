package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.*;
import nl.hsleiden.View;
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
    @JsonView(View.Public.class)
    private Long id;

    @Length(max = 10)
    @Column(name = "title")
    @JsonProperty("title")
    @JsonView(View.Public.class)
    private String title;

    @NotNull
    @Length(max = 30)
    @Column(name = "first_name")
    @JsonProperty("first_name")
    @JsonView(View.Public.class)
    private String firstName;

    @Length(max = 15)
    @Column(name = "infix")
    @JsonProperty("infix")
    @JsonView(View.Public.class)
    private String infix;

    @NotNull
    @Length(max = 30)
    @Column(name = "last_name")
    @JsonProperty("last_name")
    @JsonView(View.Public.class)
    private String lastName;

    @NotNull
    @Length(max = 50)
    @Column(name = "address")
    @JsonProperty("address")
    @JsonView(View.Public.class)
    private String address;

    @NotNull
    @Length(max = 10)
    @Column(name = "zipcode")
    @JsonProperty("zipcode")
    @JsonView(View.Public.class)
    private String zipcode;

    @NotNull
    @Length(max = 50)
    @Column(name = "country")
    @JsonProperty("country")
    @JsonView(View.Public.class)
    private String country;

    @NotNull
    @Column(name = "gender")
    @JsonProperty("gender")
    @JsonView(View.Public.class)
    private char gender;

    @NotNull
    @Length(max = 50)
    @Column(name = "city")
    @JsonProperty("city")
    @JsonView(View.Public.class)
    private String city;

    @Length(max = 250)
    @Column(name = "note")
    @JsonProperty("note")
    @JsonView(View.Public.class)
    private String note;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonProperty("email_addresses")
    @JsonView(View.Public.class)
    private Set<CustomerEmail> emails;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonProperty("customer_orders")
    @JsonView(View.Public.class)
    private Set<CustomerOrder> customerOrders;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonProperty("phone_numbers")
    @JsonView(View.Public.class)
    private Set<CustomerPhone> phones;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonView(View.Public.class)
    @JsonProperty("orders")
    @JsonBackReference("eventCustomerRef")
    private Set<Order> orders;

    public Customer() {

    }

    public Customer(Long customerid) {
        this.id = customerid;
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

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

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
