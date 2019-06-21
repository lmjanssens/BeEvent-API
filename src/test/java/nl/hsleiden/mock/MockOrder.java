package nl.hsleiden.mock;

import nl.hsleiden.model.Order;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class MockOrder extends Order {

    public MockOrder() {
        this.setDateEvent(Date.valueOf("2019-01-10"));
        this.setDateOrder(Date.valueOf("2019-01-10"));
        this.setStartTime("13:37");
        this.setEndTime("17:30");
        this.setEvent(new MockEvent());
        this.setCustomer(new MockCustomer());
    }
}
