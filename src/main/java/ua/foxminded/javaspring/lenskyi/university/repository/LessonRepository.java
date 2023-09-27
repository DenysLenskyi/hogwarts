package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Lesson findLessonByDateAndLessonTime(LocalDate date, LessonTime lessonTime);

    List<Lesson> findLessonsByDate(LocalDate date);

    boolean existsByDateAndLessonTimeAndGroup(LocalDate date, LessonTime lessonTime,
                                              Group group);

    boolean existsByDateAndLessonTimeAndSubject(LocalDate date, LessonTime lessonTime,
                                                Subject subject);
}