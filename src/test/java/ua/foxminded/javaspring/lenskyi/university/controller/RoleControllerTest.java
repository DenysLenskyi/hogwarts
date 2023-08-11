package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    @WithMockUser(username = "minervamcgonagall", roles = "admin")
    void givenRoles_whenFindAllRoles_thenReturnJsonArray() throws Exception {
        Role testRole = new Role();
        testRole.setName("test");
        List<Role> allRoles = Arrays.asList(testRole);
        given(roleRepository.findAll()).willReturn(allRoles);
        mvc.perform(MockMvcRequestBuilders
                        .get("/role/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles-db-overview"))
                .andExpect(model().attribute("roles", allRoles))
                .andExpect(model().attribute("roles", Matchers.hasSize(1)));
    }
}