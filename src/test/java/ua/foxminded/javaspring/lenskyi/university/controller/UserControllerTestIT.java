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
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.form.ProfessorForm;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.RoleService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class UserControllerTestIT {

    private static final String YOU_INPUTTED_WRONG_DATA = "you inputted wrong data";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails("minervamcgonagall")
    void getAllStudentsPageTest() throws Exception {
        List<User> students = userService.findAllStudents();
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/student"))
                .andExpect(status().isOk())
                .andExpect(view().name("students-page"))
                .andExpect(model().attribute("students", students))
                .andExpect(model().attribute("students", Matchers.hasSize(students.size())));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditStudentFormTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        UserDto studentDto = userService.findById(student.getId());
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/student/" + student.getId() + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/edit-student-form"))
                .andExpect(model().attribute("studentDto", studentDto))
                .andExpect(model().attribute("groups", groupService.findAll()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditStudentFormWrongIdTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        long wrongStudentId = student.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/student/" + wrongStudentId + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editStudentTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        UserDto studentDto = userService.findById(student.getId());
        mvc.perform(MockMvcRequestBuilders
                        .put("/user/student/" + student.getId())
                        .param("firstName", student.getFirstName())
                        .param("lastName", "test")
                        .param("username", student.getUsername())
                        .param("password", "test")
                        .param("groupDto.name", studentDto.getGroupDto().getName())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals("test", student.getLastName());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editStudentWrongUsernameTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        UserDto studentDto = userService.findById(student.getId());
        User student2 = students.get(1);
        mvc.perform(MockMvcRequestBuilders
                        .put("/user/student/" + student.getId())
                        .param("firstName", student.getFirstName())
                        .param("lastName", "test")
                        .param("username", student2.getUsername())
                        .param("password", "test")
                        .param("groupDto.name", studentDto.getGroupDto().getName())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
        assertFalse(student.getLastName().contains("test"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showCreateNewStudentFormTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/student/create-student-page")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/create-student-form"))
                .andExpect(model().attributeExists("studentDto"))
                .andExpect(model().attribute("groups", groupService.findAll()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewStudentTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/student")
                        .param("firstName", "testName")
                        .param("lastName", "lastName")
                        .param("username", "test")
                        .param("password", "test")
                        .param("groupDto.name", groupService.findAll().get(0).getName())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        User user = userService.findUserByUsername("test");
        assertEquals("lastName", user.getLastName());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewStudentNotUniqueUsernameTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        User student2 = students.get(1);
        mvc.perform(MockMvcRequestBuilders.post("/user/student")
                        .param("firstName", "testName")
                        .param("lastName", "lastName")
                        .param("username", student2.getUsername())
                        .param("password", "test")
                        .param("groupDto.name", groupService.findAll().get(0).getName())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteStudentTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        mvc.perform(MockMvcRequestBuilders.delete("/user/student/" + student.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertFalse(userService.existsById(student.getId()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteStudentWrongIdTest() throws Exception {
        List<User> students = userService.findAllStudents();
        User student = students.get(0);
        long wrongStudentId = student.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders.delete("/user/student/" + wrongStudentId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void getAllProfessorsPageTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/professor"))
                .andExpect(status().isOk())
                .andExpect(view().name("professors-page"))
                .andExpect(model().attribute("professorsAndAdmins", professors))
                .andExpect(model().attribute("professorsAndAdmins", Matchers.hasSize(professors.size())));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditProfessorFormTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User professor = professors.get(0);
        ProfessorForm professorForm = userService.createAndFillProfessorFormByUserId(professor.getId());
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/professor/" + professor.getId() + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/edit-professor-form"))
                .andExpect(model().attribute("professorForm", professorForm));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditProfessorFormWrongIdTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User professor = professors.get(0);
        long wrongProfessorId = professor.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders
                        .get("/user/professor/" + wrongProfessorId + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editProfessorTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User professor = professors.get(0);
        Role adminRole = roleService.findRoleByName("admin");
        mvc.perform(MockMvcRequestBuilders
                        .put("/user/professor/" + professor.getId())
                        .param("firstName", "test")
                        .param("lastName", "test")
                        .param("username", "testUsername")
                        .param("password", "test")
                        .param("admin", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals("test", professor.getFirstName());
        assertEquals("test", professor.getLastName());
        assertEquals("testUsername", professor.getUsername());
        assertTrue(professor.getRoles().contains(adminRole));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editProfessorWrongUsernameTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User professor = professors.get(0);
        User anotherProfessor = professors.get(1);
        mvc.perform(MockMvcRequestBuilders
                        .put("/user/professor/" + professor.getId())
                        .param("firstName", professor.getFirstName())
                        .param("lastName", "test")
                        .param("username", anotherProfessor.getUsername())
                        .param("password", "test")
                        .param("admin", "false")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
        assertFalse(professor.getLastName().contains("test"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showCreateNewProfessorFormTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/professor/create-professor-page")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/create-professor-form"))
                .andExpect(model().attributeExists("professorForm"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewProfessorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/professor")
                        .param("firstName", "testName")
                        .param("lastName", "lastName")
                        .param("username", "createNewProfessorTest")
                        .param("password", "test")
                        .param("admin", "false")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        User user = userService.findUserByUsername("createNewProfessorTest");
        assertEquals("testName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals(user.getRoles().size(), 1);
        assertTrue(user.getRoles().contains(roleService.findRoleByName("professor")));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewProfessorNotUniqueUsernameTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User anotherProfessor = professors.get(1);
        mvc.perform(MockMvcRequestBuilders.post("/user/professor")
                        .param("firstName", "testName")
                        .param("lastName", "lastName")
                        .param("username", anotherProfessor.getUsername())
                        .param("password", "test")
                        .param("admin", "false")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteProfessorTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User professor = professors.get(0);
        mvc.perform(MockMvcRequestBuilders.delete("/user/professor/" + professor.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertFalse(userService.existsById(professor.getId()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteProfessorWrongIdTest() throws Exception {
        List<User> professors = userService.findAllProfessorsAndAdmins();
        User professor = professors.get(0);
        long wrongProfessorId = professor.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders.delete("/user/professor/" + wrongProfessorId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }
}