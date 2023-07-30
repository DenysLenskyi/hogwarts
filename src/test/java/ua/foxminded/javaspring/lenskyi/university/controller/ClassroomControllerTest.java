package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassroomController.class)
class ClassroomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClassroomRepository classroomRepository;

    @Test
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