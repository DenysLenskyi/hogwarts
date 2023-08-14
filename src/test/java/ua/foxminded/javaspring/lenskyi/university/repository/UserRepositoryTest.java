package ua.foxminded.javaspring.lenskyi.university.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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
}