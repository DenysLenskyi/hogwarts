package ua.foxminded.javaspring.lenskyi.university.service;

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

    LessonDto findById(Long id);

    void createLesson(LessonDto lessonDto);
}
