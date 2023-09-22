package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonStartEndTimeDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonEntityLessonDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonStartEndTimeEntityLessonStartEndTimeDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.time.LocalDate;
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

    public boolean isBusyByGroup(LocalDate localDate, LessonStartEndTimeDto lessonStartEndTimeDto, GroupDto groupDto) {
        return lessonRepository.existsByDateAndLessonStartEndTimeAndGroup(localDate,
                lessonStartEndTimeRepository.findById(lessonStartEndTimeDto.getId())
                        .orElseThrow(IllegalArgumentException::new),
                groupService.findById(groupDto.getId()));
    }

    public boolean isBusyBySubject(LocalDate localDate, LessonStartEndTimeDto lessonStartEndTimeDto, SubjectDto subjectDto) {
        return lessonRepository.existsByDateAndLessonStartEndTimeAndSubject(localDate,
                lessonStartEndTimeRepository.findById(lessonStartEndTimeDto.getId())
                        .orElseThrow(IllegalArgumentException::new),
                subjectService.findSubjectById(subjectDto.getId()));
    }

    public LessonDto findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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

    public void createLesson(LessonDto lessonDto) {
        LessonStartEndTime lessonStartEndTime = lessonStartEndTimeRepository.findById(
                lessonDto.getLessonStartEndTimeDto().getId()).orElseThrow(IllegalArgumentException::new);
        Subject subject = subjectService.findSubjectById(lessonDto.getSubjectDto().getId());
        Group group = groupService.findById(lessonDto.getGroupDto().getId());
        Lesson lesson = new Lesson();
        lesson.setDate(lessonDto.getDate());
        lesson.setLessonStartEndTime(lessonStartEndTime);
        lesson.setSubject(subject);
        lesson.setGroup(group);
        lessonRepository.saveAndFlush(lesson);
    }
}