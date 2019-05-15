package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid", columnDefinition = "serial")
    private Long orderId;

    @Column(name = "eventid")
    private Long eventId;

    @Column(name = "customerid")
    private Long customerId;

    @Column(name = "invoicenumber")
    private Long invoiceNumber;

    @Column(name = "quotationid")
    private Long quotationId;

    @Column(name = "dateorder")
    private String dateOrder;

    @Column(name = "dateevent")
    private String dateEvent;

    @Column(name = "note")
    private String note;

    @Column(name = "starttime")
    private String startTime;

    @Column(name = "endtime")
    private String endTime;

    public Order(Long eventId, Long customerId, Long invoiceNumber, Long quotationId, String dateOrder,
                 String dateEvent, String note, String startTime, String endTime) {
        this.eventId = eventId;
        this.customerId = customerId;
        this.invoiceNumber = invoiceNumber;
        this.quotationId = quotationId;
        this.dateOrder = dateOrder;
        this.dateEvent = dateEvent;
        this.note = note;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Order() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
