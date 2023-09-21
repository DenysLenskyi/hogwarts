package ua.foxminded.javaspring.lenskyi.university;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.*;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class EntitiesCorrelationTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private LessonStartEndTimeRepository lessonStartEndTimeRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    @Transactional
    void entityCorrectnessTest() {
        assertTrue(groupRepository.findAll().size() == 1);
        assertTrue(classroomRepository.findAll().size() > 1);
        assertTrue(lessonStartEndTimeRepository.findAll().size() == 5);
        assertTrue(roleRepository.findAll().size() == 3);

        assertEquals(Long.valueOf(1), lessonStartEndTimeRepository.findAll().get(0).getId());
        assertEquals(Long.valueOf(2), lessonStartEndTimeRepository.findAll().get(1).getId());
        assertEquals(Long.valueOf(3), lessonStartEndTimeRepository.findAll().get(2).getId());

        User severus = userRepository.findUserByLastName("Snape");
        assertEquals("Severus", severus.getFirstName());
        assertEquals("professor", severus.getRoles().stream().findFirst().get().getName());

        assertEquals("Gryffindor-7", lessonRepository.findAll().get(0).getGroup().getName());

        Lesson lesson = lessonRepository.findLessonByDateAndLessonStartEndTime(
                LocalDate.of(2023, 9, 4), lessonStartEndTimeRepository.findById(1L).get());

        assertEquals("Gryffindor-7", lesson.getGroup().getName());
        assertEquals("Potions", lesson.getSubject().getName());
    }
}