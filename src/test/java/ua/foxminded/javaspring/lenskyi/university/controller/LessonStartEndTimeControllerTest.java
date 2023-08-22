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
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class LessonStartEndTimeControllerTest {

    @MockBean
    private LessonStartEndTimeRepository lessonStartEndTimeRepository;
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
    void givenLessonStartEndTimes_whenFindAllLessonStartEndTimes_thenReturnJsonArray() throws Exception {
        LessonStartEndTime testLessonStartEndTime = new LessonStartEndTime();
        testLessonStartEndTime.setStart(LocalTime.of(9, 30));
        testLessonStartEndTime.setEnd(LocalTime.of(10, 50));
        List<LessonStartEndTime> allLessonStartEndTimes = Arrays.asList(testLessonStartEndTime);
        given(lessonStartEndTimeRepository.findAll()).willReturn(allLessonStartEndTimes);
        mvc.perform(MockMvcRequestBuilders
                        .get("/lessonstartendtime/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessonstartendtimes-db-overview"))
                .andExpect(model().attribute("lessonstartendtimes", allLessonStartEndTimes))
                .andExpect(model().attribute("lessonstartendtimes", Matchers.hasSize(1)));
    }
}