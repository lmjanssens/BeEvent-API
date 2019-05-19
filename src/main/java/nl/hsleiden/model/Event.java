package nl.hsleiden.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Event Model Class
 * @author Robin Silverio
 */

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventID", columnDefinition = "SERIAL")
    private Long id;


    private Long supplierId;

    @ManyToOne
    @JoinColumn(name = "locationid")
    private EventLocation location;

    @OneToMany(mappedBy = "event")
    private Set<RegisteredEvent> registeredEvents;

    @OneToMany(mappedBy = "event")
    private Set<EventImage> eventImages;

    @Column(name = "ownEvent", nullable = false,columnDefinition = "boolean default true")
    private boolean ownEvent;

    @NotNull
    @Length(max = 30)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "program")
    private String program;

    @NotNull
    @Column(name = "duration")
    private double duration;

    @Column(name = "options")
    private String options;

    @NotNull
    @Column(name = "pricePp")
    private double pricePerPerson;

    @NotNull
    @Column(name = "priceBuyPp")
    private double priceBuyPerPerson;

    @NotNull
    @Column(name = "btw")
    private double btw;

    @NotNull
    @Column(name = "buyNotes")
    private String buyNotes;

    public Event(Long supplierId, EventLocation location, Set<RegisteredEvent> registeredEvents, Set<EventImage> eventImages, boolean ownEvent, @NotNull @Length(max = 30) String name, @NotNull String description, @NotNull String program, @NotNull double duration, String options, @NotNull double pricePerPerson, @NotNull double priceBuyPerPerson, @NotNull double btw, @NotNull String buyNotes) {
        this.supplierId = supplierId;
        this.location = location;
        this.registeredEvents = registeredEvents;
        this.eventImages = eventImages;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
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

    public String getBuyNotes() {
        return buyNotes;
    }

    public void setBuyNotes(String buyNotes) {
        this.buyNotes = buyNotes;
    }
}
