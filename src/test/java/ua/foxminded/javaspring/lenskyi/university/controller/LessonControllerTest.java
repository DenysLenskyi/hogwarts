package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LessonRepository lessonRepository;

    @Test
    @WithMockUser(username = "minervamcgonagall", roles = "admin")
    void givenLessons_whenFindAllLessons_thenReturnJsonArray() throws Exception {
//        Lesson testLesson = new Lesson();
//        testLesson.setDate(LocalDate.of(1994, 1, 20));
//        testLesson.setLessonStartEndTime(new LessonStartEndTime());
//        testLesson.setGroup(new Group());
//        testLesson.setSubjectOfTheLesson(new Subject());
//        List<Lesson> allLessons = Arrays.asList(testLesson);
//        given(lessonRepository.findAll()).willReturn(allLessons);
//        mvc.perform(MockMvcRequestBuilders
//                        .get("/lesson/all"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("lessons-db-overview"))
//                .andExpect(model().attribute("lessons", allLessons))
//                .andExpect(model().attribute("lessons", Matchers.hasSize(1)));
    }
}