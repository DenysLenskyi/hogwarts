package ua.foxminded.javaspring.lenskyi.university.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupRepository groupRepository;

    @Test
    void givenGroups_whenFindAllGroups_thenReturnJsonArray() throws Exception {
        Group testGroup = new Group();
        testGroup.setName("test");
        List<Group> allGroups = Arrays.asList(testGroup);
        given(groupRepository.findAll()).willReturn(allGroups);
        mvc.perform(MockMvcRequestBuilders
                        .get("/group/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups-db-overview"))
                .andExpect(model().attribute("groups", allGroups))
                .andExpect(model().attribute("groups", Matchers.hasSize(1)));
    }
}