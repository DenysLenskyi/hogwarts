package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@Transactional
class GroupControllerTestIT {

    private static final String YOU_INPUTTED_WRONG_DATA = "you inputted wrong data";
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showGroupsPageTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/group/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups-page"))
                .andExpect(model().attribute("groups", groupService.findAll()));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditGroupFormTest() throws Exception {
        List<GroupDto> allGroups = groupService.findAll();
        GroupDto groupToEdit = allGroups.get(0);
        allGroups.remove(groupToEdit);
        mvc.perform(MockMvcRequestBuilders
                        .get("/group/" + groupToEdit.getId() + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/edit-group-form"))
                .andExpect(model().attribute("groupsExcludeCurrent", allGroups))
                .andExpect(model().attribute("currentGroup", groupToEdit));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showEditGroupFormWrongIdTest() throws Exception {
        List<GroupDto> allGroups = groupService.findAll();
        GroupDto groupToEdit = allGroups.get(0);
        long wrongGroupId = groupToEdit.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders
                        .get("/group/" + wrongGroupId + "/edit-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void editGroupTest() throws Exception {
        Group slytherin = new Group();
        slytherin.setName("Slytherin");
        groupRepository.saveAndFlush(slytherin);
        Group gryffindor = groupService.findByName("Gryffindor-7");
        mvc.perform(MockMvcRequestBuilders
                        .put("/group/" + gryffindor.getId())
                        .param("name", slytherin.getName())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(0, userRepository.findAllByGroupName(gryffindor.getName()).size());
        assertEquals(8, userRepository.findAllByGroupName(slytherin.getName()).size());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void showCreateNewGroupFormTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/group/create-group-page")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/create-group-form"))
                .andExpect(model().attributeExists("groupDto"));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewGroupTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/group")
                        .param("name", "testName")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(2, groupService.findAll().size());
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void createNewGroupNotUniqueNameTest() throws Exception {
        List<GroupDto> allGroups = groupService.findAll();
        mvc.perform(MockMvcRequestBuilders.post("/group")
                        .param("name", allGroups.get(0).getName())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteGroupTest() throws Exception {
        Group slytherin = new Group();
        slytherin.setName("Slytherin");
        groupRepository.saveAndFlush(slytherin);
        List<GroupDto> allGroupsBeforeDelete = groupService.findAll();
        mvc.perform(MockMvcRequestBuilders.delete("/group/" + slytherin.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertTrue(allGroupsBeforeDelete.size() == 2);
        assertTrue(groupService.findAll().size() == 1);
    }

    @Test
    @WithUserDetails("minervamcgonagall")
    void deleteSubjectWrongIdTest() throws Exception {
        List<GroupDto> allGroups = groupService.findAll();
        GroupDto groupToEdit = allGroups.get(0);
        long wrongGroupId = groupToEdit.getId() - 100L;
        mvc.perform(MockMvcRequestBuilders.delete("/group/" + wrongGroupId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(YOU_INPUTTED_WRONG_DATA)));
    }
}