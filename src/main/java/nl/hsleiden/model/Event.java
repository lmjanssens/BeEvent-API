package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Event Model Class
 * @author Robin Silverio
 */

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid", columnDefinition = "SERIAL")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierid")
    @JsonIgnore
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "locationid")
    @JsonIgnore
    private EventLocation location;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<RegisteredEvent> registeredEvents;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<EventImage> eventImages;

    @Column(name = "ownevent", nullable = false,columnDefinition = "boolean default true")
    private boolean ownEvent;

    @NotNull
    @Length(max = 30)
    @Column(name = "eventname")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "eventprogram")
    private String program;

    @NotNull
    @Column(name = "duration")
    private String duration;

    @Column(name = "options")
    private String options;

    @NotNull
    @Column(name = "pricepp")
    private double pricePerPerson;

    @NotNull
    @Column(name = "pricebuypp")
    private double priceBuyPerPerson;

    @NotNull
    @Column(name = "btw")
    private double btw;

    @Column(name = "note")
    private String note;

    public Event(){ }

    public Event(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public EventLocation getLocation() {
        return location;
    }

    public void setLocation(EventLocation location) {
        this.location = location;
    }

    public Set<RegisteredEvent> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(Set<RegisteredEvent> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public Set<EventImage> getEventImages() {
        return eventImages;
    }

    public void setEventImages(Set<EventImage> eventImages) {
        this.eventImages = eventImages;
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
