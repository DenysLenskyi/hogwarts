package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;

import java.util.List;

public interface LessonService {

    List<LessonDto> findAllLessonDto();

    LessonDto findById(Long id);
}
