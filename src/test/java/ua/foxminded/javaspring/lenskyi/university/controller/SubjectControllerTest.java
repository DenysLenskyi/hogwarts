package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
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

import static org.junit.jupiter.api.Assertions.*;
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
        SubjectDto expectedSubjectDto = subjectService.findById(subjectToEdit.getId());
        mvc.perform(MockMvcRequestBuilders
                        .get("/subject/" + subjectToEdit.getId() + "/edit-page"))
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
                        .put("/subject/" + subjectToEdit.getId())
                        .param("name", subjectToEdit.getName())
                        .param("description", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals("test", subjectToEdit.getDescription());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showCreateNewSubjectFormTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/subject/create-subject-page")
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
        mvc.perform(MockMvcRequestBuilders.post("/subject")
                        .param("name", "testName")
                        .param("description", "testDescription")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        SubjectDto subject = subjectService.findByName("testName");
        assertEquals("testDescription", subject.getDescription());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteSubjectTest() throws Exception {
        SubjectDto newSubject = new SubjectDto();
        newSubject.setName("testDeleteSubject");
        newSubject.setDescription("test");
        subjectService.createNewSubjectFromSubjectDto(newSubject);
        SubjectDto subjectToDelete = subjectService.findByName("testDeleteSubject");
        mvc.perform(MockMvcRequestBuilders.delete("/subject/" + subjectToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertFalse(subjectService.doesSubjectExistById(subjectToDelete.getId()));
    }
}