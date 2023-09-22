package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Lesson findLessonByDateAndLessonStartEndTime(LocalDate date, LessonStartEndTime lessonStartEndTime);

    List<Lesson> findLessonsByDate(LocalDate date);

    boolean existsByDateAndLessonStartEndTimeAndGroup(LocalDate date, LessonStartEndTime lessonStartEndTime,
                                                      Group group);

    boolean existsByDateAndLessonStartEndTimeAndSubject(LocalDate date, LessonStartEndTime lessonStartEndTime,
                                                        Subject subject);
}