package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonStartEndTimeController.class)
class LessonStartEndTimeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LessonStartEndTimeRepository lessonStartEndTimeRepository;

    @Test
    @WithMockUser(username = "minervamcgonagall", roles = "admin")
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