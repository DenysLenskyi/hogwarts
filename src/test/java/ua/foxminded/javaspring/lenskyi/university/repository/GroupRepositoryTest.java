package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class GroupRepositoryTest {

    private static final String expectedGroupName = "Gryffindor-7";

    @Autowired
    GroupRepository groupRepository;

    @Test
    void entityCorrectnessTest() {
        Group group = groupRepository.findAll().get(0);
        assertEquals(expectedGroupName, group.getName());
    }
}