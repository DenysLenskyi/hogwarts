package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public boolean isBusyByGroup(LocalDate localDate, LessonTimeDto lessonTimeDto, GroupDto groupDto) {
        return lessonRepository.existsByDateAndLessonTimeAndGroup(localDate,
                lessonTimeRepository.findById(lessonTimeDto.getId())
                        .orElseThrow(IllegalArgumentException::new),
                groupService.findGroupById(groupDto.getId()));
    }

    @Override
    public boolean isBusyBySubject(LocalDate localDate, LessonTimeDto lessonTimeDto, SubjectDto subjectDto) {
        return lessonRepository.existsByDateAndLessonTimeAndSubject(localDate,
                lessonTimeRepository.findById(lessonTimeDto.getId())
                        .orElseThrow(IllegalArgumentException::new),
                subjectService.findSubjectById(subjectDto.getId()));
    }

    @Override
    public List<LessonDto> findAll() {
        List<Lesson> lessons = lessonRepository.findAll();
        return mapLessonsToLessonsDtoSorted(lessons);
    }

    private List<LessonDto> mapLessonsToLessonsDtoSorted(List<Lesson> lessons) {
        return lessons.stream()
                .map(lessonDtoMapper::lessonEntityToLessonDto)
                .sorted(Comparator.comparing(LessonDto::getDate)
                        .thenComparing(lesson -> lesson.getLessonTimeDto().getStart())
                        .thenComparing(lesson -> lesson.getGroupDto().getName())
                        .thenComparing(lesson -> lesson.getSubjectDto().getName()))
                .toList();
    }

    @Override
    public List<LessonDto> findAllByDate(LocalDate date) {
        List<Lesson> lessons = lessonRepository.findLessonsByDate(date);
        return mapLessonsToLessonsDtoSorted(lessons);
    }

    @Override
    public List<LessonDto> findAllByDateBetween(LocalDate start, LocalDate end) {
        List<Lesson> lessons = lessonRepository.findLessonsByDateBetween(start, end);
        return mapLessonsToLessonsDtoSorted(lessons);
    }

    @Override
    public Page<LessonDto> findAllPaginated(Pageable pageable) {

        Page<Lesson> pageLesson = lessonRepository.findAll(pageable);
        List<LessonDto> lessonDtoList = pageLesson.getContent().stream()
                .map(lessonDtoMapper::lessonEntityToLessonDto)
                .sorted(Comparator.comparing(LessonDto::getDate)
                        .thenComparing(lesson -> lesson.getLessonTimeDto().getStart())
                        .thenComparing(lesson -> lesson.getGroupDto().getName())
                        .thenComparing(lesson -> lesson.getSubjectDto().getName()))
                .toList();
        return new PageImpl<>(lessonDtoList, PageRequest.of(
                pageLesson.getNumber(), pageLesson.getSize()), pageLesson.getTotalElements());

    }

    @Override
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

    @Override
    public boolean existsById(Long id) {
        return lessonRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}