package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @MockBean
    private UserServiceImpl userServiceImpl;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void shouldRedirectAnonymousUserToLogin() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void allUsersViewTest() throws Exception {
        User testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        Role testRole = new Role();
        testRole.setName("test");
        testUser.setRoles(Set.of(testRole));
        List<User> allUsers = Arrays.asList(testUser);
        given(userServiceImpl.findAllUsers()).willReturn(allUsers);
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-db-overview"))
                .andExpect(model().attribute("users", allUsers))
                .andExpect(model().attribute("users", Matchers.hasSize(1)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditFormTest() throws Exception {
        UserDto userDto = new UserDto();
        given(userServiceImpl.getUserDtoByUserId(isA(Long.class))).willReturn(userDto);
        given(userServiceImpl.doesUserExistById(isA(Long.class))).willReturn(true);
        List<User> allUsers = userRepository.findAll();
        mvc.perform(MockMvcRequestBuilders.get("/user/edit/" + allUsers.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userDto));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void updateUserRolesTest() throws Exception {
        List<User> allUsers = userRepository.findAll();
        mvc.perform(MockMvcRequestBuilders.put("/user/edit/" + allUsers.get(0).getId())
                .param("checkboxSelectedValues", "admin").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/all"));
    }
}