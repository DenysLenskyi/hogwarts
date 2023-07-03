package ua.foxminded.javaspring.lenskyi.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class CreateTablesTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private LessonStartEndTimeRepository lessonStartEndTimeRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void test() {
        assertTrue(groupRepository.findAll().size() == 1);
        assertTrue(classroomRepository.findAll().size() > 1);
        assertTrue(lessonStartEndTimeRepository.findAll().size() == 5);
        assertTrue(roleRepository.findAll().size() == 3);
    }
}