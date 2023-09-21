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
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class LessonControllerTest {

    @MockBean
    private LessonRepository lessonRepository;
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
    void givenLessons_whenFindAllLessons_thenReturnJsonArray() throws Exception {
        Lesson testLesson = new Lesson();
        testLesson.setDate(LocalDate.of(1994, 1, 20));
        testLesson.setLessonStartEndTime(new LessonStartEndTime());
        testLesson.setGroup(new Group());
        testLesson.setSubject(new Subject());
        List<Lesson> allLessons = Arrays.asList(testLesson);
        given(lessonRepository.findAll()).willReturn(allLessons);
        mvc.perform(MockMvcRequestBuilders
                        .get("/lesson/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons-page"))
                .andExpect(model().attribute("lessons", allLessons))
                .andExpect(model().attribute("lessons", Matchers.hasSize(1)));
    }
}