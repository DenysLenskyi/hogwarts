package ua.foxminded.javaspring.lenskyi.university.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Transactional
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonTimeRepository lessonTimeRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SubjectService subjectService;

    @Test
    void lessonGotGroupTest() {
        List<Lesson> testLessons = lessonRepository.findLessonsByDate(LocalDate.of(2023, 9, 18));
        Lesson lesson = lessonRepository.findById(testLessons.get(0).getId()).orElseThrow();
        assertEquals("Gryffindor-7", lesson.getGroup().getName());
    }

    @Test
    void lessonGotSubjectTest() {
        List<Lesson> testLessons = lessonRepository.findLessonsByDate(LocalDate.of(2023, 9, 18));
        Lesson lesson = lessonRepository.findById(testLessons.get(0).getId()).orElseThrow();
        assertTrue(!lesson.getSubject().getName().isEmpty());
    }

    @Test
    void getAllByDateTest() {
        List<Lesson> lessons = lessonRepository.findLessonsByDate(LocalDate.of(2023, 9, 18));
        assertEquals(4, lessons.size());
    }
}