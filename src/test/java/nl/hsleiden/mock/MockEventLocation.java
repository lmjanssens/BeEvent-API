package nl.hsleiden.mock;

import nl.hsleiden.model.EventLocation;

import javax.persistence.Entity;

@Entity
public class MockEventLocation extends EventLocation {

    public MockEventLocation() {
        this.setName("Delft");
        this.setDescription("It is a nice place");
        this.setRoutePicture("image/denhaag.jpg");
    }
}
