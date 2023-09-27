package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonTimeDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonTimeEntityLessonTimeDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.LessonTimeService;

import java.util.List;

@Service
public class LessonTimeServiceImpl implements LessonTimeService {

    private final LessonTimeRepository repository;
    private final LessonTimeEntityLessonTimeDtoMapper mapper;

    public LessonTimeServiceImpl(LessonTimeRepository repository,
                                 LessonTimeEntityLessonTimeDtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LessonTimeDto findById(Long id) {
        return mapper.lessonTimeEntityToLessonTimeDto(
                repository.findById(id).orElseThrow()
        );
    }

    public List<LessonTimeDto> findAllDto() {
        List<LessonTime> lessonTimes = repository.findAll();
        return lessonTimes.stream()
                .map(mapper::lessonTimeEntityToLessonTimeDto)
                .toList();
    }
}