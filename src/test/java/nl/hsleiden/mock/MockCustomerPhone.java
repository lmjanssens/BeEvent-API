package nl.hsleiden.mock;

import nl.hsleiden.model.CustomerPhone;

public class MockCustomerPhone extends CustomerPhone {

    public MockCustomerPhone() {
        this.setId(1L);
        this.setPhone("+31 6 12345666");
    }
}
