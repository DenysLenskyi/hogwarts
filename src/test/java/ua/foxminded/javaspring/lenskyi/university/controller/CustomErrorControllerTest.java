package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class CustomErrorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails("harrypotter")
    void studentGoesToCreateNewSubject_shouldReturn403() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/creation-page"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void adminGoesToCreateNewSubject_shouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/creation-page"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("remuslupin")
    void professorGoesToEditSubject_shouldReturn200() throws Exception {
        List<SubjectDto> subjects = subjectService.findAll();
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/" + subjects.get(0).getId() + "/edit-page"))
                .andExpect(status().isOk());
    }
}