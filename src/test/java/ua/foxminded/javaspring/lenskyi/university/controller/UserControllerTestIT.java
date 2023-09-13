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
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    private SubjectService subjectService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ClassroomService classroomService;
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
}