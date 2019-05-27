package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "customerorder")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerorderid", columnDefinition = "serial")
    private Long customerOrderId;

    //TODO: add order, moet dit in de order controller of customer controller?

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonIgnore
    private Customer customer;

    public Long getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(Long customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
