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
import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class ClassroomControllerTestIT {

    private static final String YOU_INPUTTED_WRONG_DATA = "you inputted wrong data";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ClassroomService classroomService;

    @Test
    @WithUserDetails("minervamcgonagall")
    void getAllClassroomPageTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        mvc.perform(MockMvcRequestBuilders
                        .get("/classroom/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("classrooms-page"))
                .andExpect(model().attribute("classrooms", Matchers.equalToObject(allClassrooms)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditClassroomFormTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        Classroom classroom = allClassrooms.get(0);
        ClassroomDto classroomDto = classroomService.findById(classroom.getId());
        mvc.perform(MockMvcRequestBuilders
                        .get("/classroom/" + classroom.getId() + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/edit-classroom-form"))
                .andExpect(model().attribute("classroomDto", classroomDto));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditClassroomFormWrongIdTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        long wrongClassroomId = allClassrooms.get(0).getId() - 100L;
        mvc.perform(MockMvcRequestBuilders
                        .get("/classroom/" + wrongClassroomId + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editClassroomTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        Classroom classroom = allClassrooms.get(0);
        mvc.perform(MockMvcRequestBuilders
                        .put("/classroom/" + classroom.getId())
                        .param("name", classroom.getName())
                        .param("description", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals("test", classroom.getDescription());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editClassroomWrongNameTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        Classroom classroom = allClassrooms.get(0);
        Classroom classroomTwo = allClassrooms.get(1);
        mvc.perform(MockMvcRequestBuilders
                        .put("/classroom/" + classroom.getId())
                        .param("name", classroomTwo.getName())
                        .param("description", "test")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
        assertFalse(classroom.getDescription().contains("test"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showCreateNewClassroomFormTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/classroom/create-classroom-page")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/create-classroom-form"))
                .andExpect(model().attributeExists("classroomDto"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewClassroomTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/classroom")
                        .param("name", "testName")
                        .param("description", "testDescription")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertTrue(classroomService.existsByName("testName"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewClassroomNotUniqueNameTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        mvc.perform(MockMvcRequestBuilders.post("/classroom")
                        .param("name", allClassrooms.get(0).getName())
                        .param("description", "testDescription")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteClassroomTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        Classroom classroom = allClassrooms.get(0);
        mvc.perform(MockMvcRequestBuilders.delete("/classroom/" + classroom.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertFalse(classroomService.existsByName(classroom.getName()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteClassroomWrongIdTest() throws Exception {
        List<Classroom> allClassrooms = classroomService.findAll();
        Classroom classroom = allClassrooms.get(0);
        long wrongClassroomId = classroom.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders.delete("/classroom/" + wrongClassroomId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }
}