package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import jakarta.persistence.*;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.time.LocalDate;

public class LessonDto {

    private Long id;
    private LocalDate date;
    private LessonStartEndTimeDto lessonStartEndTime;
    private SubjectDto subjectOfTheLesson;
    private GroupDto group;


}
