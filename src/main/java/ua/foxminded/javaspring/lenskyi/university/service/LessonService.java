package ua.foxminded.javaspring.lenskyi.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonTimeDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {

    boolean isBusyByGroup(LocalDate localDate, LessonTimeDto lessonTime, GroupDto group);

    boolean isBusyBySubject(LocalDate localDate, LessonTimeDto lessonTime, SubjectDto subject);

    List<LessonDto> findAll();

    List<LessonDto> findAllByDate(LocalDate date);

    List<LessonDto> findAllByDateBetween(LocalDate start, LocalDate end);

    Page<LessonDto> findAllPaginated(Pageable pageable);

    void createLesson(LessonDto lessonDto);

    boolean existsById(Long id);

    void deleteById(Long id);
}
