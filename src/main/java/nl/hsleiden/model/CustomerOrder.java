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

    @ManyToOne
    @JoinColumn(name = "orderid")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonIgnore
    private Customer customer;

    public CustomerOrder(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
    }

    public Long getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(Long customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
