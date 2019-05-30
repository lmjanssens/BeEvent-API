package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer_email")
public class CustomerEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customeremailid", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonIgnore
    private Customer customer;

    @NotNull
    @Email
    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerEmail(@NotNull @Email String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        CustomerEmail email = (CustomerEmail) object;
        return this.email.equals(email.getEmail());
    }
}
