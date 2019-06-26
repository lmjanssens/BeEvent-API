package nl.hsleiden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hsleiden.mock.*;
import nl.hsleiden.model.Customer;
import nl.hsleiden.model.Event;
import nl.hsleiden.model.EventLocation;
import nl.hsleiden.model.Supplier;
import nl.hsleiden.repository.CustomerRepository;
import nl.hsleiden.repository.EventLocationRepository;
import nl.hsleiden.repository.EventRepository;
import nl.hsleiden.repository.SupplierRepository;
import nl.hsleiden.util.AuthenticationUtil;
import nl.hsleiden.util.DatabaseTestService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseTestService databaseTestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @Autowired
    private EventRepository eventRepository;

    private static Customer customer = new MockCustomer();
    private static Supplier supplier = new MockSupplier();

    private static EventLocation eventLocation = new MockEventLocation();
    private static Event event = new MockEvent();

    @BeforeEach
    public void setup() {
        databaseTestService.setupUsers();

        customerRepository.save(customer);
        supplierRepository.save(supplier);
        eventLocationRepository.save(eventLocation);

        event.setSupplier(supplier);
        event.setLocation(eventLocation);

        eventRepository.save(event);
    }

    public OrderControllerTest() { }

    @Test
    @Order(1)
    void createOrder() throws Exception {
        mockMvc.perform(post("/api/orders/" + customer.getId() + "/" + event.getId())
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(new MockOrder())
                ).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));;
    }

    @Test
    @Order(2)
    void getOrders() throws Exception {
        mockMvc.perform(get("/api/orders")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$").value(notNullValue()));
    }

    @Test
    @Order(3)
    void getSpecifiedXatering() throws Exception {
        MockOrder order = new MockOrder();

        mockMvc.perform(get("/api/orders/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.note").value(order.getNote())
        ).andExpect(jsonPath("$.startTime").value(order.getStartTime())
        ).andExpect(jsonPath("$.endTime").value(order.getEndTime())
        ).andExpect(jsonPath("$.persons").value(order.getPersons())
        ).andExpect(jsonPath("$.event").value(notNullValue()))
        .andExpect(jsonPath("$.customer").value(notNullValue()));
    }

    @Test
    @Order(4)
    void updateOrder() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/orders/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        nl.hsleiden.model.Order order = objectMapper.readValue(result.getResponse().getContentAsString(), nl.hsleiden.model.Order.class);
        order.setNote("notitie");

        mockMvc.perform(put("/api/orders/1/" + customer.getId() + "/" + event.getId())
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value(order.getNote()));
    }

    @Test
    @Order(5)
    void deleteOrder() {
//        mockMvc.perform(delete("/api/orders/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isOk());

//        mockMvc.perform(get("/api/orders/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isNotFound());
    }
}