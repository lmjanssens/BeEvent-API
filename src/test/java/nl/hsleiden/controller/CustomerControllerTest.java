package nl.hsleiden.controller;

import nl.hsleiden.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void getCustomers() {

    }

    @Test
    void getSpecifiedCustomer() throws Exception {
        System.out.println(mockMvc);
        mockMvc.perform(get("/api/customers/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.gender").value('v'))
                .andExpect(jsonPath("$.first_name").value("Niels"))
                .andExpect(jsonPath("$.infix").value("der"))
                .andExpect(jsonPath("$.last_name").value("achternaam"))
                .andExpect(jsonPath("$.address").value("Straat 123"))
                .andExpect(jsonPath("$.zipcode").value("2323JA"))
                .andExpect(jsonPath("$.country").value("Nederland"))
                .andExpect(jsonPath("$.city").value("Alkmaar"))
                .andExpect(jsonPath("$.title").value("heer"))
                .andReturn();
    }

    @Test
    void createCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}