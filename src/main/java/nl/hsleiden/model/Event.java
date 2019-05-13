package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Event Model Class
 * @author Robin Silverio
 */

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId", columnDefinition = "SERIAL")
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierId", columnDefinition = "serial")
    private Long supplierId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationId", columnDefinition = "serial")
    private Long locationId;

    @Column(name = "ownEvent", nullable = false,columnDefinition = "boolean default true")
    private boolean ownEvent;

    @Length(max = 30)
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    // At that moment, this property has a datatype of Object due to implementation of a database.
    @Column(name = "program")
    private Object program;

    @Column(name = "duration")
    private double duration;

    // At that moment, this property has a datatype of Object due to implementation of a database.
    @Column(name = "options")
    private Object options;

    @Column(name = "pricePp")
    private double pricePerPerson;

    @Column(name = "priceBuyPp")
    private double priceBuyPerPerson;

    @Column(name = "btw")
    private double btw;

    @Column(name = "buyNotes")
    private String buyNotes;

    /**
     * A Constructor for creating an Event object
     * @param ownEvent
     * @param name
     * @param description
     * @param program
     * @param duration
     * @param options
     * @param pricePerPerson
     * @param priceBuyPerPerson
     * @param btw
     * @param buyNotes
     */
    public Event(boolean ownEvent, @Length(max = 30) String name, String description, Object program, double duration, Object options, double pricePerPerson, double priceBuyPerPerson, double btw, String buyNotes) {
        this.ownEvent = ownEvent;
        this.name = name;
        this.description = description;
        this.program = program;
        this.duration = duration;
        this.options = options;
        this.pricePerPerson = pricePerPerson;
        this.priceBuyPerPerson = priceBuyPerPerson;
        this.btw = btw;
        this.buyNotes = buyNotes;
    }

    public Event(){ }

    /**
     * Here are the getters and setters for obtaining event properties
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOwnEvent() {
        return ownEvent;
    }

    public void setOwnEvent(boolean ownEvent) {
        this.ownEvent = ownEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getProgram() {
        return program;
    }

    public void setProgram(Object program) {
        this.program = program;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Object getOptions() {
        return options;
    }

    public void setOptions(Object options) {
        this.options = options;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public double getPriceBuyPerPerson() {
        return priceBuyPerPerson;
    }

    public void setPriceBuyPerPerson(double priceBuyPerPerson) {
        this.priceBuyPerPerson = priceBuyPerPerson;
    }

    public double getBtw() {
        return btw;
    }

    public void setBtw(double btw) {
        this.btw = btw;
    }

    public String getBuyNotes() {
        return buyNotes;
    }

    public void setBuyNotes(String buyNotes) {
        this.buyNotes = buyNotes;
    }
}
