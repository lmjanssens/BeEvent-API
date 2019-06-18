package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;

import javax.persistence.*;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerorderid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long customerOrderId;

    @ManyToOne
    @JoinColumn(name = "orderid")
    @JsonBackReference("customerOrderOrderRef")
    @JsonView(View.Public.class)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    @JsonBackReference("customerOrderCustomerRef")
    @JsonView(View.Public.class)
    private Customer customer;

    public CustomerOrder(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
    }

    public CustomerOrder() {

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
