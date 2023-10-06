package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.service.RoleService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class RoleControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleService roleService;

    @Test
    @WithUserDetails("minervamcgonagall")
    void getRolePageTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/role/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles-page"))
                .andExpect(model().attribute("roles", roleService.findAll()))
                .andExpect(model().attribute("roles", Matchers.hasSize(3)));
    }
}