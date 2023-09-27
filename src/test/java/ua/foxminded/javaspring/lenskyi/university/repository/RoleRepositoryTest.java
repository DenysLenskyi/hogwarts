package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void entityCorrectnessTest() {
        assertEquals(3, roleRepository.findAll().size());
        assertEquals("student", roleRepository.findRoleByName("student").orElseThrow().getName());
        assertEquals("professor", roleRepository.findRoleByName("professor").orElseThrow().getName());
        assertEquals("admin", roleRepository.findRoleByName("admin").orElseThrow().getName());
    }
}