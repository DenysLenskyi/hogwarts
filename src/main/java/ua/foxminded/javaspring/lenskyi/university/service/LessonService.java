package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonStartEndTimeDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {

    boolean isBusyByGroup(LocalDate localDate, LessonStartEndTimeDto lessonStartEndTime, GroupDto group);
    boolean isBusyBySubject(LocalDate localDate, LessonStartEndTimeDto lessonStartEndTime, SubjectDto subject);

    List<LessonDto> findAllLessonDto();

    LessonDto findById(Long id);

    void createLesson(LessonDto lessonDto);
}
