package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "customerorder")
public class CustomerOrder {
    //TODO: Kijk of dit een goede implementatie is, moet misschien veranderd worden in de database, maak composite primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerorderid", columnDefinition = "serial")
    private Long customerOrderId;

    @Column(name = "customerid")
    private Long customerId;

    @Column(name = "orderid")
    private Long orderId;

    public CustomerOrder(Long customerId, Long orderId, Long customerOrderId) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.customerOrderId = customerOrderId;
    }

    public CustomerOrder() {

    }

    public Long getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(Long customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
