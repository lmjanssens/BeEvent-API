package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "event_location")
public class EventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationid")
    @JsonView(View.Public.class)
    private Long id;

    @NotNull
    @Length(max = 255)
    @Column(name = "name")
    @JsonView(View.Public.class)
    private String name;

    @Length(max = 255)
    @Column(name = "description")
    @JsonView(View.Public.class)
    private String description;

    @Length(max = 255)
    @Column(name = "routepicture")
    @JsonView(View.Public.class)
    private String routePicture;

    public EventLocation(@NotNull @Length(max = 255) String name, @NotNull @Length(max = 255) String description, @NotNull @Length(max = 255) String routePicture) {
        this.name = name;
        this.description = description;
        this.routePicture = routePicture;
    }

    public EventLocation() {

    }

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

    public String getRoutePicture() {
        return routePicture;
    }

    public void setRoutePicture(String routePicture) {
        this.routePicture = routePicture;
    }
}
