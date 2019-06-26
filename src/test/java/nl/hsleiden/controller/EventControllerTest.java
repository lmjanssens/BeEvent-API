package nl.hsleiden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hsleiden.mock.MockEvent;
import nl.hsleiden.mock.MockEventLocation;
import nl.hsleiden.mock.MockOrder;
import nl.hsleiden.mock.MockSupplier;
import nl.hsleiden.model.Event;
import nl.hsleiden.model.EventLocation;
import nl.hsleiden.model.Supplier;
import nl.hsleiden.repository.EventLocationRepository;
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
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseTestService databaseTestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    private static Supplier supplier = new MockSupplier();
    private static EventLocation eventLocation = new MockEventLocation();

    @BeforeEach
    public void setup() {
        databaseTestService.setupUsers();

        supplierRepository.save(supplier);
        eventLocationRepository.save(eventLocation);
    }

    @Test
    @Order(1)
    void createEvent() throws Exception {
        mockMvc.perform(post("/api/events/" + supplier.getId() + "/" + eventLocation.getId())
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(new MockEvent())
                ).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        ;
    }

    @Test
    @Order(2)
    void getEvents() throws Exception {
        mockMvc.perform(get("/api/events")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$").value(notNullValue()));
    }

    @Test
    @Order(3)
    void getSpecifiedEvents() throws Exception {
        MockEvent event = new MockEvent();

        mockMvc.perform(get("/api/events/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.name").value(event.getName())
        ).andExpect(jsonPath("$.btw").value(event.getBtw())
        ).andExpect(jsonPath("$.duration").value(event.getDuration())
        ).andExpect(jsonPath("$.description").value(event.getDescription())
        ).andExpect(jsonPath("$.note").value(event.getNote())
        ).andExpect(jsonPath("$.supplier").value(notNullValue()))
                .andExpect(jsonPath("$.location").value(notNullValue()));
    }


    @Test
    @Order(4)
    void updateEvent() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/events/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Event event = objectMapper.readValue(result.getResponse().getContentAsString(), Event.class);
        event.setNote("notitie");

        mockMvc.perform(put("/api/events/1/" + supplier.getId() + "/" + eventLocation.getId())
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value(event.getNote()));
    }

    @Test
    @Order(5)
    void deleteEvents() {
//        mockMvc.perform(delete("/api/orders/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isOk());

//        mockMvc.perform(get("/api/orders/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isNotFound());
    }
}