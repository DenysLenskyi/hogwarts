package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.transaction.Transactional;
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
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.LessonTimeService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@Transactional
class LessonTimeControllerTest {

    @Autowired
    private LessonTimeService lessonTimeService;
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
    @WithUserDetails("minervamcgonagall")
    void showLessonTimesPageTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/lesson-time/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessontimes-db-overview"))
                .andExpect(model().attribute("lessontimes", lessonTimeService.findAllDto()))
                .andExpect(model().attribute("lessontimes", Matchers.hasSize(5)));
    }
}