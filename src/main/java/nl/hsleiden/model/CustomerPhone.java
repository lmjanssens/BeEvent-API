package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer_phone")
public class CustomerPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerphoneid", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonIgnore
    private Customer customer;

    @NotNull
    @Column(name = "phonenumber")
    @JsonProperty("phonenumber")
    private String phone;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerPhone(@NotNull String phone) {
        this.phone = phone;
    }

    public CustomerPhone() {

    }

    @Override
    public boolean equals(Object object) {
        CustomerPhone phone = (CustomerPhone) object;
        return this.phone.equals(phone.getPhone());
    }
}
