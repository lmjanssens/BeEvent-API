package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

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

    @Length(max = 30)
    @Column(name = "name")
    private String name;

    @Length(max = 255)
    @Column(name = "description")
    private String description;

    @Length(max = 255)
    @Column(name = "routepicture")
    private String routepicture;

    /**
     * Constructor for creating eventlocation object.
     * @param name
     * @param description
     * @param routepicture
     */
    public EventLocation(@Length(max = 30) String name, @Length(max = 255) String description, @Length(max = 255) String routepicture) {
        this.name = name;
        this.description = description;
        this.routepicture = routepicture;
    }

    public EventLocation() { }

    /**
     * Getters and setters
     *
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
