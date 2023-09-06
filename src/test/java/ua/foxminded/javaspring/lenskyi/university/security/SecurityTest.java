package ua.foxminded.javaspring.lenskyi.university.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecurityTest {

    private final static String SUBJECT_PAGE = "/subject/all";
    private final static String CREATE_SUBJECT_PAGE = "/subject/create-subject-page";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SubjectService subjectService;

    @Test
    void notAuthenticatedUserTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get(SUBJECT_PAGE))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void whenAdminOpensAnyPage_thenReturn200() throws Exception {
        List<Subject> allSubjects = subjectService.findAll();
        final String editSubjectUrl = "/subject/" + allSubjects.get(0).getId() + "/edit-page";
        mvc.perform(MockMvcRequestBuilders.get(SUBJECT_PAGE))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get(CREATE_SUBJECT_PAGE))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get(editSubjectUrl))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("remuslupin")
    void whenProfessorOpensEditSubjectPage_thenReturn200() throws Exception {
        List<Subject> allSubjects = subjectService.findAll();
        final String editSubjectUrl = "/subject/" + allSubjects.get(0).getId() + "/edit-page";
        mvc.perform(MockMvcRequestBuilders.get(editSubjectUrl))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("remuslupin")
    void whenProfessorOpensCreateSubjectPage_thenReturn403() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(CREATE_SUBJECT_PAGE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("harrypotter")
    void whenStudentOpensCreateSubjectPage_thenReturn403() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(CREATE_SUBJECT_PAGE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("harrypotter")
    void whenStudentOpensSubjectPage_thenReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(SUBJECT_PAGE))
                .andExpect(status().isOk());
    }
}