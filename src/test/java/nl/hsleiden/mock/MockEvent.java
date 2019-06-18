package nl.hsleiden.mock;

import nl.hsleiden.model.Event;

public class MockEvent extends Event {

    public MockEvent() {
        this.setId(1L);
        this.setOwnEvent(true);
        this.setName("Mock event");
        this.setDescription("This is a great event");
        this.setProgram("Fietstocht");
        this.setDuration("1:30");
        this.setOptions(null);
        this.setPricePerPerson(130.5);
        this.setPriceBuyPerPerson(0);
        this.setBtw(0.21);
        this.setNote(null);
        this.setMaxInstructors(5);
        this.setLocation(new MockEventLocation());
    }
}
