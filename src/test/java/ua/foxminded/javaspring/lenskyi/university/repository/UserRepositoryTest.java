package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void entityCorrectnessTest() {
        User severus = userRepository.findUserByLastName("Snape");
        User harry = userRepository.findUserByLastName("Potter");
        assertEquals("Snape", severus.getLastName());
        assertEquals("Gryffindor-7", harry.getGroup().getName());
    }
}