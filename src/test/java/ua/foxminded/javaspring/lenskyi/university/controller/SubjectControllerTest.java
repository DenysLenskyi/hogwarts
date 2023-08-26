package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class SubjectControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails("minervamcgonagall")
    void getAllSubjectPageTest() throws Exception {
        List<Subject> allSubjects = subjectService.findAll();
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects-db-overview"))
                .andExpect(model().attribute("subjects", Matchers.equalToObject(allSubjects)))
                .andExpect(model().attribute("subjects", Matchers.hasSize(12)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditSubjectFormTest() throws Exception {
        List<Subject> allSubjects = subjectService.findAll();
        Subject subjectToEdit = allSubjects.get(0);
        SubjectDto expectedSubjectDto = subjectService.findSubjectDtoById(subjectToEdit.getId());
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/edit/" + subjectToEdit.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/edit-subject-form"))
                .andExpect(model().attribute("subjectDto", expectedSubjectDto))
                .andExpect(model().attribute("freeClassrooms", classroomService.findAllFreeClassrooms()))
                .andExpect(model().attribute("freeProfessors", userService.findAllProfessorsWithNoSubject()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editSubjectTest() throws Exception {
        List<Subject> allSubjects = subjectService.findAll();
        Subject subjectToEdit = allSubjects.get(0);
        mvc.perform(MockMvcRequestBuilders
                        .put("/subject/edit/" + subjectToEdit.getId())
                        .param("name", subjectToEdit.getName())
                        .param("description", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals("test", subjectToEdit.getDescription());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showCreateNewSubjectFormTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/subject/new")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/create-subject"))
                .andExpect(model().attributeExists("subjectDto"))
                .andExpect(model().attribute("freeClassrooms", classroomService.findAllFreeClassrooms()))
                .andExpect(model().attribute("freeProfessors", userService.findAllProfessorsWithNoSubject()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewSubjectTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/subject/new")
                        .param("name", "testName")
                        .param("description", "testDescription")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Subject subject = subjectService.findByName("testName").orElseThrow();
        assertEquals("testDescription", subject.getDescription());
    }
}