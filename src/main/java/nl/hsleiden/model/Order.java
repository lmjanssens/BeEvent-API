package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid", columnDefinition = "serial")
    private Long orderId;

    @Column(name = "customerid") //TODO: bij customer verwerken
    @JsonProperty("customerid")
    private Long customerId;

    @Column(name = "dateorder")
    @JsonProperty("dateorder")
    private Date dateOrder;

    @Column(name = "dateevent")
    @JsonProperty("dateevent")
    private String dateEvent;

    @Column(name = "note")
    @JsonProperty("note")
    private String note;

    @Column(name = "starttime")
    @JsonProperty("starttime")
    private Timestamp startTime;

    @Column(name = "endtime")
    @JsonProperty("endtime")
    private Timestamp endTime;

    @Column(name = "maxinstructors")
    @JsonProperty("maxinstructors")
    private int maxInstructors;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    @JsonProperty("catering_orders")
    private Set<CateringOrder> cateringOrders;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    @JsonProperty("invoices")
    private Set<Invoice> invoices;

    //TODO: add events

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getMaxInstructors() {
        return maxInstructors;
    }

    public void setMaxInstructors(int maxInstructors) {
        this.maxInstructors = maxInstructors;
    }

    public Set<CateringOrder> getCateringOrders() {
        return cateringOrders;
    }

    public void setCateringOrders(Set<CateringOrder> cateringOrders) {
        this.cateringOrders = cateringOrders;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
