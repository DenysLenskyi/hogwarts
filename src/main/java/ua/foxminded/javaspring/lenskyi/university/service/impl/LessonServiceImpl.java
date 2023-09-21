package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonEntityLessonDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonStartEndTimeEntityLessonStartEndTimeDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonEntityLessonDtoMapper lessonDtoMapper;
    private final LessonStartEndTimeEntityLessonStartEndTimeDtoMapper lessonStartEndTimeDtoMapper;
    private final GroupEntityGroupDtoMapper groupDtoMapper;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final LessonStartEndTimeRepository lessonStartEndTimeRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonEntityLessonDtoMapper lessonDtoMapper,
                             SubjectService subjectService, GroupService groupService,
                             LessonStartEndTimeRepository lessonStartEndTimeRepository,
                             GroupEntityGroupDtoMapper groupDtoMapper,
                             LessonStartEndTimeEntityLessonStartEndTimeDtoMapper lessonStartEndTimeDtoMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonDtoMapper = lessonDtoMapper;
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.lessonStartEndTimeRepository = lessonStartEndTimeRepository;
        this.groupDtoMapper = groupDtoMapper;
        this.lessonStartEndTimeDtoMapper = lessonStartEndTimeDtoMapper;
    }

    public LessonDto findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        LessonDto lessonDto = lessonDtoMapper.lessonEntityToLessonDto(lesson);
        lessonDto.setLessonStartEndTimeDto(lessonStartEndTimeDtoMapper
                .lessonStartEndTimeEntityToLessonStartEndTimeDto(lesson.getLessonStartEndTime()));
        if (lesson.getGroup() != null) {
            lessonDto.setGroupDto(groupDtoMapper.groupEntityToGroupDto(lesson.getGroup()));
        }
        if (lesson.getSubject() != null) {
            lessonDto.setSubjectDto(subjectService.findById(lesson.getSubject().getId()));
        }
        return lessonDto;
    }

    public List<LessonDto> findAllLessonDto() {
        return lessonRepository.findAll().stream()
                .map(lesson -> findById(lesson.getId()))
                .toList();
    }
}