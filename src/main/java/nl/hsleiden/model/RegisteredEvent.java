package nl.hsleiden.model;

import javax.persistence.*;

/**
 * @author Robin Silverio
 */

@Entity
@Table(name = "registered_event")
public class RegisteredEvent {

    @ManyToOne
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    public RegisteredEvent(Event event) {
        this.event = event;
    }

    public RegisteredEvent() {}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
