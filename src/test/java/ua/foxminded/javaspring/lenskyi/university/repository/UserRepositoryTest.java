package ua.foxminded.javaspring.lenskyi.university.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    void entityCorrectnessTest() {
        User severus = userRepository.findUserByLastName("Snape");
        User harry = userRepository.findUserByFirstName("Harry");
        User user = userRepository.findById(13L).get();
        assertEquals("Snape", severus.getLastName());
        assertEquals("Gryffindor-7", harry.getGroup().getName());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void findUserByUsernameTest() {
        User harrypotter = userRepository.findUserByUsername("harrypotter");
        assertEquals("Harry", harrypotter.getFirstName());
    }

    @Test
    void findUserByFirstAndLastName() {
        User user = userRepository.findUserByFirstNameAndLastName("Harry", "Potter");
        assertEquals("Harry", user.getFirstName());
    }

    @Test
    void existByIdTest() {
        List<User> allUsers = userRepository.findAll();
        assertTrue(userRepository.existsById(allUsers.get(0).getId()));
        assertFalse(userRepository.existsById(10000L));
    }

    @Test
    @Transactional
    void findAllProfessorsWithNoSubjectTest() {
        User user = new User();
        user.setFirstName("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setRoles(Set.of(roleRepository.findRoleByName("professor").orElseThrow()));
        userRepository.save(user);
        List<User> allProfessorsWithNoSubject = userRepository.findAllBySubjectIsNullAndRolesContains(roleRepository.findRoleByName("professor").orElseThrow());
        assertEquals(1, allProfessorsWithNoSubject.size());
        assertEquals("test", allProfessorsWithNoSubject.get(0).getFirstName());
    }

    @Test
    void countAllByGroupNameTest() {
        long numStudsFromGryffindor = userRepository.countAllByGroupName("Gryffindor-7");
        assertEquals(8, numStudsFromGryffindor);
    }

    @Test
    void findAllByGroupNameTest() {
        assertEquals(8, userRepository.findAllByGroupName("Gryffindor-7").size());
    }

    @Test
    void findAllNonStudentTest() {
        Role admin = roleRepository.findRoleByName("admin").orElseThrow();
        Role professor = roleRepository.findRoleByName("professor").orElseThrow();
        List<Role> roles = List.of(admin, professor);
        List<User> professorsAndAdmins = userRepository.findAllByRolesIsIn(roles);
        assertEquals(12, professorsAndAdmins.size());
    }

    @Test
    void deleteByWrongIdTest() {
        List<User> users = userRepository.findAll();
        userRepository.deleteById(users.get(0).getId() + 666000L);
    }
}