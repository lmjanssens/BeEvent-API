package nl.hsleiden.service;

import nl.hsleiden.mock.MockInvoice;
import nl.hsleiden.mock.MockOrder;
import nl.hsleiden.model.Customer;
import nl.hsleiden.model.Event;
import nl.hsleiden.model.Invoice;
import nl.hsleiden.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailParserServiceTest {

    private EmailParserService service;

    private Order order;
    private Event event;
    private Customer customer;

    @BeforeEach
    void setUp() {
        this.service = new EmailParserService();

        this.order = new MockOrder();
        this.event = this.order.getEvent();
        this.customer =  this.order.getCustomer();
    }

    @Test
    void parseTitle() {
        String actual = this.service.parse("<titel>", this.order);
        String expected = this.customer.getTitle();

        assertEquals(expected, actual);
    }

    @Test
    void parseFirstname() {
        String actual = this.service.parse("<voornaam>", this.order);
        String expected = this.customer.getFirstName();

        assertEquals(expected, actual);
    }

    @Test
    void parseLastname() {
        String actual = this.service.parse("<achternaam>", this.order);
        String expected = ((this.customer.getInfix() != null) ? this.customer.getInfix() + " ": "") + this.customer.getLastName();

        assertEquals(expected, actual);
    }

    @Test
    void parseEvent() {
        String actual = this.service.parse("<evenement>", this.order);
        String expected = this.event.getName();

        assertEquals(expected, actual);
    }

    @Test
    void parseOrderEventDate() {
        String actual = this.service.parse("<evenementdatum>", this.order);
        String expected = this.order.getDateEvent().toString();

        assertEquals(expected, actual);
    }
}