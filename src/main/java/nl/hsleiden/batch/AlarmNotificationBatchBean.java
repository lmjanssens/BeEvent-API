package nl.hsleiden.batch;


import nl.hsleiden.model.Customer;
import nl.hsleiden.model.CustomerEmail;
import nl.hsleiden.model.Invoice;
import nl.hsleiden.repository.InvoiceRepository;
import nl.hsleiden.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class AlarmNotificationBatchBean {

    private final Logger LOGGER = LoggerFactory.getLogger(AlarmNotificationBatchBean.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DateService dateService;

    @Scheduled(cron = "0 0 8 * * *")
    public void cronJob() {
        LOGGER.info("> Cron Job");

        Date currentDate = new Date();
        Collection<Invoice> invoices = invoiceRepository.findAllWhereDateFullPaidIsNullAndExcludeFromInvoiceAlertIsFalse();

        for (Invoice invoice : invoices) {
            Date invoiceDate = invoice.getDateInvoiceMailSent();
            Date notificationDate = dateService.addWeeks(invoiceDate, 2);

            if (currentDate.after(notificationDate))
                this.sendNotificationEmailToCustomer(invoice);
        }

        LOGGER.info("< Cron Job");
    }

    private void sendNotificationEmailToCustomer(Invoice invoice) {
        Customer customer = invoice.getOrder().getCustomer();
        Collection<CustomerEmail> emails = customer.getEmails();

        if (emails.size() > 0) {
            CustomerEmail email = emails.iterator().next();
            System.out.println(email);

            // TODO: Write logic to send mail to the customer
        }
    }
}
