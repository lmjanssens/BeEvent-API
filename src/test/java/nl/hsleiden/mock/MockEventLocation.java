package nl.hsleiden.mock;

import nl.hsleiden.model.EventLocation;

public class MockEventLocation extends EventLocation {

    public MockEventLocation() {
        this.setId(1L);
        this.setName("Delft");
        this.setDescription("It is a nice place");
        this.setRoutePicture("image/denhaag.jpg");
    }
}
