package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonStartEndTimeDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonStartEndTimeEntityLessonStartEndTimeDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.LessonStartEndTimeService;

import java.util.List;

@Service
public class LessonStartEndTimeServiceImpl implements LessonStartEndTimeService {

    private final LessonStartEndTimeRepository repository;
    private final LessonStartEndTimeEntityLessonStartEndTimeDtoMapper mapper;

    public LessonStartEndTimeServiceImpl(LessonStartEndTimeRepository repository,
                                         LessonStartEndTimeEntityLessonStartEndTimeDtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LessonStartEndTimeDto findById(Long id) {
        return mapper.lessonStartEndTimeEntityToLessonStartEndTimeDto(
                repository.findById(id).orElseThrow()
        );
    }

    public List<LessonStartEndTimeDto> findAllDto() {
        return repository.findAll().stream()
                .map(e -> findById(e.getId()))
                .toList();
    }
}