package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class LessonRepositoryTest {

    @Autowired
    LessonRepository lessonRepository;

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
        assertTrue(!lesson.getSubjectOfTheLesson().getName().isEmpty());
    }
}