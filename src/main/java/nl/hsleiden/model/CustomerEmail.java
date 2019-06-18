package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer_email")
public class CustomerEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customeremailid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonBackReference("customerMailPhone")
    @JsonView(View.Public.class)
    private Customer customer;

    @NotNull
    @Email
    @Column(name = "email")
    @JsonProperty("email")
    @JsonView(View.Public.class)
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

    @Override
    public boolean equals(Object object) {
        CustomerEmail email = (CustomerEmail) object;
        return this.email.equals(email.getEmail());
    }
}
