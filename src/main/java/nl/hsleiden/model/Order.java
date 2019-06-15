package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.*;
import nl.hsleiden.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false , updatable = false)
    @JsonProperty("customerid")
    private Customer customer;

    @Column(name = "dateorder")
    @JsonProperty("dateorder")
    private Date dateOrder;

    @Column(name = "dateevent")
    @JsonProperty("dateevent")
    private Date dateEvent;

    @Column(name = "note")
    @JsonProperty("note")
    private String note;

    @Column(name = "starttime")
    @JsonProperty("starttime")
    private Timestamp startTime;

    @Column(name = "endtime")
    @JsonProperty("endtime")
    private Timestamp endTime;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonProperty("catering_orders")
    private Set<CateringOrder> cateringOrders;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonProperty("invoices")
    private Set<Invoice> invoices;

    @OneToOne
    @JoinColumn(name = "eventid")
    @JsonProperty("eventid")
    private Event event;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonProperty("quotations")
    private Set<Quotation> quotations;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(Set<Quotation> quotations) {
        this.quotations = quotations;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
