package nl.hsleiden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hsleiden.mock.MockCustomer;
import nl.hsleiden.model.Customer;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseTestService databaseTestService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        databaseTestService.setupUsers();
    }

    public CustomerControllerTest() {
    }

    @Test
    @Order(1)
    void createCustomer() throws Exception {
        mockMvc.perform(post("/api/customers")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(new MockCustomer())
                )
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    @Order(2)
    void getCustomers() throws Exception {
        mockMvc.perform(get("/api/customers")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", is(notNullValue())));
    }

    @Test
    @Order(3)
    void getSpecifiedCustomer() throws Exception {
        MockCustomer customer = new MockCustomer();

        mockMvc.perform(get("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.gender").value(Character.toString(customer.getGender())))
                .andExpect(jsonPath("$.first_name").value(customer.getFirstName()))
                .andExpect(jsonPath("$.infix").value(customer.getInfix()))
                .andExpect(jsonPath("$.last_name").value(customer.getLastName()))
                .andExpect(jsonPath("$.address").value(customer.getAddress()))
                .andExpect(jsonPath("$.zipcode").value(customer.getZipcode()))
                .andExpect(jsonPath("$.country").value(customer.getCountry()))
                .andExpect(jsonPath("$.city").value(customer.getCity()))
                .andExpect(jsonPath("$.title").value(customer.getTitle()));
    }

    @Test
    @Order(4)
    void updateCustomer() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/customers/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Customer customer = objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
        customer.setTitle("mevrouw");

        mockMvc.perform(put("/api/customers/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(customer.getTitle()));
    }

    @Test
    @Order(5)
    void deleteCustomer() throws Exception {
//        mockMvc.perform(delete("/api/customers/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isOk());

//        mockMvc.perform(get("/api/customers/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isNotFound());
    }
}