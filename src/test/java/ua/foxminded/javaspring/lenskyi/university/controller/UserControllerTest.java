package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void givenUsers_whenFindAllUsers_thenReturnJsonArray() throws Exception {
        User testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        List<User> allUsers = Arrays.asList(testUser);
        given(userRepository.findAll()).willReturn(allUsers);
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/db"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-db-overview"))
                .andExpect(model().attribute("users", allUsers))
                .andExpect(model().attribute("users", Matchers.hasSize(1)));
    }
}