package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class ClassroomControllerTest {

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

    @MockBean
    private ClassroomRepository classroomRepository;

    @Test
    @WithUserDetails("minervamcgonagall")
    void givenClassrooms_whenFindAllClassrooms_thenReturnJsonArray() throws Exception {
        Classroom testClassroom = new Classroom();
        testClassroom.setName("test");
        testClassroom.setDescription("test");
        List<Classroom> allClassrooms = Arrays.asList(testClassroom);
        given(classroomRepository.findAll()).willReturn(allClassrooms);
        mvc.perform(MockMvcRequestBuilders
                        .get("/classroom/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("classrooms-db-overview"))
                .andExpect(model().attribute("classrooms", allClassrooms))
                .andExpect(model().attribute("classrooms", Matchers.hasSize(1)));
    }
}