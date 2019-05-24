package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid", columnDefinition = "serial")
    private Long orderId;

    @Column(name = "eventid")
    @JsonProperty("eventid")
    private Long eventId;

    @Column(name = "customerid")
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
}
