package nl.hsleiden.service;

import nl.hsleiden.model.Customer;
import nl.hsleiden.model.Invoice;
import nl.hsleiden.model.Order;
import org.springframework.stereotype.Component;

@Component
public class EmailParserService {

    public String parse(String mail, Invoice invoice) {

        Order order = invoice.getOrder();
        Customer customer = order.getCustomer();

        return mail
                .replace("<titel>", customer.getTitle())
                .replace("<voornaam>", customer.getFirstName())
                .replace("<achternaam>", ((customer.getInfix() != null) ? customer.getInfix() + " ": "") + customer.getLastName())
                .replace("<evenement>", order.getEvent().getName())
                .replace("<evenementdatum>", order.getDateEvent().toString())
                .replace("<openstaandbedrag>", Double.toString(invoice.getToBePaid()).replace('.', ','))
                .replace("<factuurnummer>", invoice.getInvoiceNumber().toString());
    }
}
