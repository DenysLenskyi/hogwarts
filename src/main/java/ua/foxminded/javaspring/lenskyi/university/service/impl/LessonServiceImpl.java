package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonTimeDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonEntityLessonDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.SubjectEntitySubjectDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonEntityLessonDtoMapper lessonDtoMapper;
    private final SubjectEntitySubjectDtoMapper subjectDtoMapper;
    private final GroupEntityGroupDtoMapper groupDtoMapper;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final LessonTimeRepository lessonTimeRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonEntityLessonDtoMapper lessonDtoMapper,
                             SubjectService subjectService, GroupService groupService,
                             LessonTimeRepository lessonTimeRepository,
                             GroupEntityGroupDtoMapper groupDtoMapper,
                             SubjectEntitySubjectDtoMapper subjectDtoMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonDtoMapper = lessonDtoMapper;
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.lessonTimeRepository = lessonTimeRepository;
        this.groupDtoMapper = groupDtoMapper;
        this.subjectDtoMapper = subjectDtoMapper;
    }

    public boolean isBusyByGroup(LocalDate localDate, LessonTimeDto lessonTimeDto, GroupDto groupDto) {
        return lessonRepository.existsByDateAndLessonTimeAndGroup(localDate,
                lessonTimeRepository.findById(lessonTimeDto.getId())
                        .orElseThrow(IllegalArgumentException::new),
                groupService.findGroupById(groupDto.getId()));
    }

    public boolean isBusyBySubject(LocalDate localDate, LessonTimeDto lessonTimeDto, SubjectDto subjectDto) {
        return lessonRepository.existsByDateAndLessonTimeAndSubject(localDate,
                lessonTimeRepository.findById(lessonTimeDto.getId())
                        .orElseThrow(IllegalArgumentException::new),
                subjectService.findSubjectById(subjectDto.getId()));
    }

    public List<LessonDto> findAll() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream()
                .map(lesson -> {
                    LessonDto lessonDto = lessonDtoMapper.lessonEntityToLessonDto(lesson);
                    lessonDto.setGroupDto(groupDtoMapper.groupEntityToGroupDto(lesson.getGroup()));
                    lessonDto.setSubjectDto(subjectDtoMapper.subjectEntityToSubjectDto(lesson.getSubject()));
                    return lessonDto;
                })
                .toList();
    }

    public LessonDto findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        LessonDto lessonDto = lessonDtoMapper.lessonEntityToLessonDto(lesson);
        lessonDto.setGroupDto(groupDtoMapper.groupEntityToGroupDto(lesson.getGroup()));
        lessonDto.setSubjectDto(subjectService.findById(lesson.getSubject().getId()));
        return lessonDto;
    }

    @Transactional
    public void createLesson(LessonDto lessonDto) {
        LessonTime lessonTime = lessonTimeRepository.findById(
                lessonDto.getLessonTimeDto().getId()).orElseThrow(IllegalArgumentException::new);
        Subject subject = subjectService.findSubjectById(lessonDto.getSubjectDto().getId());
        Group group = groupService.findGroupById(lessonDto.getGroupDto().getId());
        Lesson lesson = new Lesson();
        lesson.setDate(lessonDto.getDate());
        lesson.setLessonTime(lessonTime);
        lesson.setSubject(subject);
        lesson.setGroup(group);
        lessonRepository.saveAndFlush(lesson);
    }
}