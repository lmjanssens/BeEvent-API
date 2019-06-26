package nl.hsleiden.service;

import nl.hsleiden.mock.MockInvoice;
import nl.hsleiden.model.Customer;
import nl.hsleiden.model.Event;
import nl.hsleiden.model.Invoice;
import nl.hsleiden.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailParserServiceTest {

    private EmailParserService service;

    private Invoice invoice;
    private Order order;
    private Event event;
    private Customer customer;

    @BeforeEach
    void setUp() {
        this.service = new EmailParserService();

        this.invoice = new MockInvoice();
        this.invoice.setInvoiceNumber(1L);

        this.order = this.invoice.getOrder();
        this.event = this.order.getEvent();
        this.customer =  this.order.getCustomer();
    }

    @Test
    void parseTitle() {
        String actual = this.service.parse("<titel>", this.invoice);
        String expected = this.customer.getTitle();

        assertEquals(expected, actual);
    }

    @Test
    void parseFirstname() {
        String actual = this.service.parse("<voornaam>", this.invoice);
        String expected = this.customer.getFirstName();

        assertEquals(expected, actual);
    }

    @Test
    void parseLastname() {
        String actual = this.service.parse("<achternaam>", this.invoice);
        String expected = ((this.customer.getInfix() != null) ? this.customer.getInfix() + " ": "") + this.customer.getLastName();

        assertEquals(expected, actual);
    }

    @Test
    void parseEvent() {
        String actual = this.service.parse("<evenement>", this.invoice);
        String expected = this.event.getName();

        assertEquals(expected, actual);
    }

    @Test
    void parseOrderEventDate() {
        String actual = this.service.parse("<evenementdatum>", this.invoice);
        String expected = this.order.getDateEvent().toString();

        assertEquals(expected, actual);
    }

    @Test
    void parseToBePaid() {
        String actual = this.service.parse("<openstaandbedrag>", this.invoice);
        String expected = Double.toString(this.invoice.getToBePaid()).replace('.', ',');


        assertEquals(expected, actual);
    }

    @Test
    void parseInvoiceNumber() {
        String actual = this.service.parse("<factuurnummer>", this.invoice);
        String expected = this.invoice.getInvoiceNumber().toString();

        assertEquals(expected, actual);
    }
}