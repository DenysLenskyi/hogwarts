package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SubjectRepository subjectRepository;

    @Test
    void givenSubjects_whenFindAllSubjects_thenReturnJsonArray() throws Exception {
        Subject testSubject = new Subject();
        testSubject.setName("test");
        testSubject.setDescription("test");
        testSubject.setClassroom(new Classroom());
        List<Subject> allRoles = Arrays.asList(testSubject);
        given(subjectRepository.findAll()).willReturn(allRoles);
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects-db-overview"))
                .andExpect(model().attribute("subjects", allRoles))
                .andExpect(model().attribute("subjects", Matchers.hasSize(1)));
    }
}