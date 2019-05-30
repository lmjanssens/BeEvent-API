package nl.hsleiden.controllers;

import nl.hsleiden.controller.CustomerController;
import nl.hsleiden.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private CustomerController customerController;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testGetCustomer() throws Exception {
        mockMvc.perform(get("/api/customers/1")
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
    public void testGetCustomers() throws Exception {
//        mockMvc.perform(get("/api/customers")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.customers").exists())
//                .andExpect(jsonPath("$.customers.customerid").isNotEmpty())
//                .andReturn();
        when(customerRepository.findAll()).thenReturn(
                Collections.emptyList()
        );

        MvcResult mvcResult = mockMvc.perform(get("/api/customers")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        System.out.println(mvcResult.getResponse());

        verify(customerRepository).findAll();
    }

    @Test
    public void testPostCustomer() throws Exception {
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonMock()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gender", Matchers.is("m")))
                .andExpect(jsonPath("$.first_name", Matchers.is("Luuk")))
                .andExpect(jsonPath("$.infix", Matchers.is("van")))
                .andExpect(jsonPath("$.last_name", Matchers.is("Geestig")))
                .andExpect(jsonPath("$.address", Matchers.is("Bloemenlaan 89")))
                .andExpect(jsonPath("$.zipcode", Matchers.is("2929LA")))
                .andExpect(jsonPath("$.country", Matchers.is("Nederland")))
                .andExpect(jsonPath("$.city", Matchers.is("Coevorden")))
                .andExpect(jsonPath("$.title", Matchers.is("heer")))
                .andReturn();
    }

    @Test
    public void testPutCustomer() throws Exception {
        mockMvc.perform(put("api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonMock()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gender", Matchers.is("m")))
                .andExpect(jsonPath("$.first_name", Matchers.is("Luuk")))
                .andExpect(jsonPath("$.infix", Matchers.is("van")))
                .andExpect(jsonPath("$.last_name", Matchers.is("Geestig")))
                .andExpect(jsonPath("$.address", Matchers.is("Bloemenlaan 89")))
                .andExpect(jsonPath("$.zipcode", Matchers.is("2929LA")))
                .andExpect(jsonPath("$.country", Matchers.is("Nederland")))
                .andExpect(jsonPath("$.city", Matchers.is("Coevorden")))
                .andExpect(jsonPath("$.title", Matchers.is("heer")))
                .andReturn();
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    public String getJsonMock() {
        return "{\n" +
                "  \"gender\": \"m\",\n" +
                "  \"first_name\": \"Luuk\",\n" +
                "  \"infix\": \"van\",\n" +
                "  \"last_name\": \"Geestig\",\n" +
                "  \"address\": \"Bloemenlaan 89\",\n" +
                "  \"zipcode\": \"2929LA\",\n" +
                "  \"country\": \"Nederland\",\n" +
                "  \"city\": \"Coevorden\",\n" +
                "  \"title\": \"heer\",\n" +
                "  \"email-addresses\": \"[\", \n" +
                "  \"{ \"email\": \"test@test.com\" }, \n" +
                "  ],\n" +
                "  \"phone-numbers\": \"[\", \n" +
                "  \"{ \"phonenumber\": \"06-43430303\" }, \n" +
                "  ],\n" +
                "}";
    }
}
