package nl.hsleiden.mock;

import nl.hsleiden.model.Order;

import java.sql.Date;
import java.sql.Timestamp;

public class MockOrder extends Order {

    public MockOrder() {
        this.setOrderId(1L);
        this.setDateEvent(Date.valueOf("2019-01-10"));
        this.setDateOrder(Date.valueOf("2019-01-10"));
        this.setStartTime("13:37:42");
        this.setEndTime("17:37:42");
        this.setEvent(new MockEvent());
        this.setCustomer(new MockCustomer());
    }
}
