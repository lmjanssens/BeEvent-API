package nl.hsleiden.model;

import javax.persistence.*;

/**
 * @author Robin Silverio
 */

@Entity
@Table(name = "registered_event")
public class RegisteredEvent {

    @Id
    @Column(name = "event_id", updatable = false, insertable = true)
    private Long id;

    @Id
    @Column(name="instructor_id", updatable=false, insertable=true)
    private Long event_id;

    public RegisteredEvent(Long id, Long event_id) {
        this.id = id;
        this.event_id = event_id;
    }

    /**
     * Getters and setters for getting id of registered Event
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }
}
