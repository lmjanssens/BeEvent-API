package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "customer_phone")
public class CustomerPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerphoneid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonBackReference("customerPhoneRef")
    @JsonView(View.Public.class)
    private Customer customer;

    @NotNull
    @Length(max = 10)
    @Pattern(regexp="(^[0-9]{10}$)")
    @Column(name = "phonenumber")
    @JsonProperty("phonenumber")
    @JsonView(View.Public.class)
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

    @Override
    public boolean equals(Object object) {
        CustomerPhone phone = (CustomerPhone) object;
        return this.phone.equals(phone.getPhone());
    }
}
