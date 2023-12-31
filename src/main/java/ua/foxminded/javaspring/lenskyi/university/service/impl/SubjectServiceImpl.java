package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.ClassroomEntityClassroomDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.LessonEntityLessonDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.SubjectEntitySubjectDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.UserEntityUserDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final SubjectEntitySubjectDtoMapper subjectMapper;
    private final ClassroomEntityClassroomDtoMapper classroomMapper;
    private final UserEntityUserDtoMapper userMapper;
    private final LessonEntityLessonDtoMapper lessonMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ClassroomRepository classroomRepository,
                              UserRepository userRepository, SubjectEntitySubjectDtoMapper subjectMapper,
                              ClassroomEntityClassroomDtoMapper classroomMapper, UserEntityUserDtoMapper userMapper,
                              LessonEntityLessonDtoMapper lessonMapper) {
        this.subjectRepository = subjectRepository;
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.subjectMapper = subjectMapper;
        this.classroomMapper = classroomMapper;
        this.userMapper = userMapper;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public List<SubjectDto> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(subjectMapper::subjectEntityToSubjectDto)
                .sorted(Comparator.comparing(SubjectDto::getId))
                .toList();
    }

    @Override
    public SubjectDto findById(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(NoSuchElementException::new);
        SubjectDto subjectDto = subjectMapper.subjectEntityToSubjectDto(subject);
        subjectDto.setClassroomDto(classroomMapper.classroomEntityToClassroomDto(subject.getClassroom()));
        subjectDto.setLessonsDto(subject.getLessons().stream()
                .map(lessonMapper::lessonEntityToLessonDto)
                .collect(Collectors.toSet()));
        subjectDto.setUserDto(userMapper.userEntityToUserDto(subject.getUser()));
        return subjectDto;
    }

    @Override
    public Subject findSubjectById(Long id) {
        return subjectRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public SubjectDto findByName(String subjectName) {
        return subjectMapper.subjectEntityToSubjectDto(subjectRepository.findSubjectByName(subjectName)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public void updateSubject(SubjectDto subjectDto) {
        Subject subjectToUpdate = subjectRepository.findById(subjectDto.getId()).orElseThrow();
        subjectToUpdate.setName(subjectDto.getName());
        subjectToUpdate.setDescription(subjectDto.getDescription());
        Classroom classroom = classroomRepository.findByName(subjectDto.getClassroomDto().getName()).orElse(null);
        subjectToUpdate.setClassroom(classroom);
        subjectToUpdate.setUser(null);
        if (subjectDto.getUserDto() != null) {
            User user = userRepository.findUserByUsername(subjectDto.getUserDto().getUsername());
            subjectToUpdate.setUser(user);
        }
        subjectRepository.saveAndFlush(subjectToUpdate);
    }

    @Override
    public boolean doesSubjectExistById(Long subjectId) {
        return subjectRepository.existsById(subjectId);
    }

    @Override
    @Transactional
    public void createNewSubject(SubjectDto subjectDto) {
        Subject newSubject = new Subject();
        newSubject.setName(subjectDto.getName());
        newSubject.setDescription(subjectDto.getDescription());
        Classroom classroom = classroomRepository.findByName(subjectDto.getClassroomDto().getName()).orElse(null);
        newSubject.setClassroom(classroom);
        newSubject.setUser(null);
        if (subjectDto.getUserDto() != null) {
            User user = userRepository.findUserByUsername(subjectDto.getUserDto().getUsername());
            newSubject.setUser(user);
        }
        subjectRepository.saveAndFlush(newSubject);
    }

    @Override
    @Transactional
    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String subjectName) {
        return subjectRepository.existsByName(subjectName);
    }

    @Override
    public List<Subject> findAllSubjectsWithNoProfessor() {
        return subjectRepository.findAllByUserIsNull();
    }
}