package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class LessonTimeRepositoryTest {

    @Autowired
    LessonTimeRepository lessonTimeRepository;

    @Test
    void entityCorrectnessTest() {
        LessonTime firstLesson = lessonTimeRepository.findById(1L).orElseThrow();
        LessonTime secondLesson = lessonTimeRepository.findById(2L).orElseThrow();
        LessonTime fifthLesson = lessonTimeRepository.findById(5L).orElseThrow();
        assertEquals(Optional.of(1L), Optional.of(firstLesson.getId()));
        assertEquals(LocalTime.of(9, 30), firstLesson.getStart());
        assertEquals(LocalTime.of(10, 50), firstLesson.getEnd());
        assertEquals(Optional.of(2L), Optional.of(secondLesson.getId()));
        assertEquals(LocalTime.of(11, 0), secondLesson.getStart());
        assertEquals(LocalTime.of(12, 20), secondLesson.getEnd());
        assertEquals(Optional.of(5L), Optional.of(fifthLesson.getId()));
        assertEquals(LocalTime.of(16, 0), fifthLesson.getStart());
        assertEquals(LocalTime.of(17, 20), fifthLesson.getEnd());
    }
}