package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonTimeDto;

import java.util.List;

public interface LessonTimeService {

    LessonTimeDto findById(Long id);

    List<LessonTimeDto> findAllDto();
}
