package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonStartEndTimeDto;

import java.util.List;

public interface LessonStartEndTimeService {

    LessonStartEndTimeDto findById(Long id);

    List<LessonStartEndTimeDto> findAllDto();
}
