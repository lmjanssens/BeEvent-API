package nl.hsleiden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hsleiden.mock.MockSupplier;
import nl.hsleiden.model.Supplier;
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
public class SupplierControllerTest {

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

    public SupplierControllerTest() {
    }

    @Test
    @Order(1)
    void createSupplier() throws Exception {
        mockMvc.perform(post("/api/suppliers")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(new MockSupplier())
                ).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.supplierid").value(1));
    }

    @Test
    @Order(2)
    void getSuppliers() throws Exception {
        mockMvc.perform(get("/api/suppliers")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$").value(notNullValue()));
    }

    @Test
    @Order(3)
    void getSupplier() throws Exception {
        MockSupplier supplier = new MockSupplier();

        mockMvc.perform(get("/api/suppliers/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.supplierid").value(1))
                .andExpect(jsonPath("$.name").value(supplier.getName()))
                .andExpect(jsonPath("$.website").value(supplier.getWebsite()))
                .andExpect(jsonPath("$.note").value(supplier.getNote()))
                .andExpect(jsonPath("$.contact_person").value(supplier.getContactPerson()))
                .andExpect(jsonPath("$.supervisor").value(supplier.getSupervisor()))
                .andExpect(jsonPath("$.image").value(supplier.getImage()))
                .andExpect(jsonPath("$.email_addresses[0]").value(notNullValue()))
                .andExpect(jsonPath("$.phone_numbers[0]").value(notNullValue()))
                .andExpect(jsonPath("$.addresses[0]").value(notNullValue()));
    }

    @Test
    @Order(4)
    void updateSupplier() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/suppliers/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Supplier supplier = objectMapper.readValue(result.getResponse().getContentAsString(), Supplier.class);
        supplier.setName("Bakker Jan");

        mockMvc.perform(put("/api/suppliers/1")
                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplier))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(supplier.getName()));
    }

    @Test
    @Order(5)
    void deleteSupplier() {
//        mockMvc.perform(delete("/api/suppliers/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isOk());

//        mockMvc.perform(get("/api/suppliers/1")
//                .header("Authorization", AuthenticationUtil.getAdministratorAuthentication()))
//                .andExpect(status().isNotFound());
    }
}