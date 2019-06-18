package nl.hsleiden.mock;

import nl.hsleiden.model.Invoice;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class MockInvoice extends Invoice {
    public MockInvoice() {
        this.setInvoiceNumber(1L);
        this.setDateInvoice(Date.valueOf("2019-01-10"));

        this.setPaymentExtras("Dit kost te veel");
        this.setPricePp(1242);
        this.setPriceBtw(12453);
        this.setOtherCosts(120);
        this.setOtherCostsBtw(190);
        this.setToBePaid(1242);
        this.setPaid(4);
        this.setDatePartPaid(Date.valueOf("2019-01-10"));
        this.setDateFullPaid(Date.valueOf("2019-01-10"));
        this.setBankAccount("INGB13371337");
        this.setDateInvoiceMailSent(Date.valueOf("2019-01-10"));
        this.setExcludeFromInvoiceAlert(false);

        this.setOrder(new MockOrder());
    }
}
