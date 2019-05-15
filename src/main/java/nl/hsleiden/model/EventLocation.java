package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

/**
 * Event model class
 * @author Robin Silverio
 */
@Entity
@Table(name = "event_location")
public class EventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", columnDefinition = "SERIAL")
    private Long id;

    @OneToMany(mappedBy = "event_location")
    private Set<Event> events;

    @Length(max = 30)
    @Column(name = "name")
    private String name;

    @Length(max = 255)
    @Column(name = "description")
    private String description;

    @Length(max = 255)
    @Column(name = "routepicture")
    private String routepicture;

    public EventLocation(Set<Event> events, @Length(max = 30) String name, @Length(max = 255) String description, @Length(max = 255) String routepicture) {
        this.events = events;
        this.name = name;
        this.description = description;
        this.routepicture = routepicture;
    }

    public EventLocation() {}

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
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

    public String getRoutepicture() {
        return routepicture;
    }

    public void setRoutepicture(String routepicture) {
        this.routepicture = routepicture;
    }
}
