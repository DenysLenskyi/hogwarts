package ua.foxminded.javaspring.lenskyi.university.security;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.lenskyi.university.controller.ClassroomController;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClassroomController.class)
@Import({TestConfig.class, SecurityConfig.class})
class SecurityTest {

    private static final String urlTemplate = "/classroom/all";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassroomService classroomService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private HogwartsUserDetailService hogwartsUserDetailService;

    @Test
    @WithUserDetails(value = "someAdminUsernameForTest", userDetailsServiceBeanName = "hogwartsUserDetailService")
    void getClassroomPage_ShouldReturn200() throws Exception {
        String expectedNameOfPage = "classrooms-db-overview";
        String expectedAttrName = "classrooms";

        Classroom testClassroom = new Classroom();
        testClassroom.setId(1L);
        testClassroom.setName("test");
        testClassroom.setDescription("test");
        List<Classroom> allClassrooms = Arrays.asList(testClassroom);

        given(classroomService.findAll()).willReturn(allClassrooms);

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedNameOfPage))
                .andExpect(model().attribute(expectedAttrName, Matchers.hasSize(allClassrooms.size())));

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate).with(user("studentUsername")))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate).with(user("professorUsername")))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate).with(anonymous()))
                .andExpect(status().isFound());
    }

//    @Test
//    @WithUserDetails(value = "studentUsername", userDetailsServiceBeanName = "hogwartsUserDetailService")
//    void getClassroomPage_ShouldReturn403() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate))
//                .andExpect(status().isForbidden());
//    }
}