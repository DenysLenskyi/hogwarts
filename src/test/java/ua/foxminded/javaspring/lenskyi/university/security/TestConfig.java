package ua.foxminded.javaspring.lenskyi.university.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

import java.util.Set;

import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    @Primary
    public UserDetailsService hogwartsUserDetailService() {

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("admin");

        Role studentRole = new Role();
        studentRole.setId(2L);
        studentRole.setName("student");

        Role professorRole = new Role();
        professorRole.setId(3L);
        professorRole.setName("professor");

        User u = new User();
        u.setFirstName("fn");
        u.setLastName("ln");
        u.setUsername("someAdminUsernameForTest");
        u.setPassword("pass");
        u.setRoles(Set.of(adminRole));

        User s = new User();
        s.setFirstName("student");
        s.setLastName("student");
        s.setUsername("studentUsername");
        s.setPassword("pass");
        s.setRoles(Set.of(studentRole));

        User p = new User();
        p.setFirstName("prof");
        p.setLastName("prof");
        p.setUsername("professorUsername");
        p.setPassword("pass");
        p.setRoles(Set.of(professorRole));

        when(userRepository.findUserByUsername("someAdminUsernameForTest")).thenReturn(u);
        when(userRepository.findUserByUsername("studentUsername")).thenReturn(s);
        when(userRepository.findUserByUsername("professorUsername")).thenReturn(p);

        return new HogwartsUserDetailService(userRepository);
    }
}