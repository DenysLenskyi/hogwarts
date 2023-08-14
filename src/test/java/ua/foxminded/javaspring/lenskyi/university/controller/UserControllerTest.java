package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.security.MethodSecurityConfig;
import ua.foxminded.javaspring.lenskyi.university.security.SecurityConfig;
import ua.foxminded.javaspring.lenskyi.university.service.RoleService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(username = "minervamcgonagall", authorities = "admin")
    void givenUsers_whenFindAllUsers_thenReturnJsonArray() throws Exception {
        User testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        Role testRole = new Role();
        testRole.setName("test");
        testUser.setRoles(Set.of(testRole));
        List<User> allUsers = Arrays.asList(testUser);
        given(userService.findAllUsers()).willReturn(allUsers);
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-db-overview"))
                .andExpect(model().attribute("users", allUsers))
                .andExpect(model().attribute("users", Matchers.hasSize(1)));
    }

    @Test
    void shouldRedirectAnonymousUserToLogin() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldThrowExceptionWhenStudentGoesToEditUsers() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/user/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("harrypotter").roles("student")))
                .andExpect(status().isForbidden());
    }
}