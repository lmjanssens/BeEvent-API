package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "event_image")
public class EventImage {

    @ManyToOne
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    @NotNull
    @Length(max = 255)
    private String imagePath;

    public EventImage(Event event, @NotNull @Length(max = 255) String imagePath) {
        this.event = event;
        this.imagePath = imagePath;
    }

    public EventImage() {}


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
