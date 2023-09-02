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

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public SubjectDto findById(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(NoSuchElementException::new);
        SubjectDto subjectDto = subjectMapper.subjectEntityToSubjectDto(subject);
        subjectDto.setClassroomDto(classroomMapper.classroomEntityToClassroomDto(subject.getClassroom()));
        subjectDto.setLessonsDto(subject.getLessons().stream().map(lessonMapper::lessonEntityToLessonDto).collect(Collectors.toSet()));
        subjectDto.setUserDto(userMapper.userEntityToUserDto(subject.getUser()));
        return subjectDto;
    }

    public SubjectDto findByName(String subjectName) {
        return subjectMapper.subjectEntityToSubjectDto(subjectRepository.findSubjectByName(subjectName)
                .orElseThrow(NoSuchElementException::new));
    }

    @Transactional
    public void updateSubjectFromSubjectDto(SubjectDto subjectDto) {
        Subject subjectToUpdate = subjectRepository.findById(subjectDto.getId()).orElseThrow();
        if (!subjectDto.getName().isEmpty()) {
            subjectToUpdate.setName(subjectDto.getName());
        }
        if (!subjectDto.getDescription().isEmpty()) {
            subjectToUpdate.setDescription(subjectDto.getDescription());
        }
        if (subjectDto.getClassroomDto() != null) {
            Classroom classroom = classroomRepository.findByName(subjectDto.getClassroomDto().getName()).orElseThrow();
            subjectToUpdate.setClassroom(classroom);
        }
        if (subjectDto.getUserDto() != null) {
            User user = userRepository.findUserByUsername(subjectDto.getUserDto().getUsername());
            subjectToUpdate.setUser(user);
        }
        subjectRepository.saveAndFlush(subjectToUpdate);
    }

    public boolean doesSubjectExistById(Long subjectId) {
        return subjectRepository.existsById(subjectId);
    }

    @Transactional
    public void createNewSubjectFromSubjectDto(SubjectDto subjectDto) {
        Subject newSubject = new Subject();
        if (!subjectDto.getName().isEmpty()) {
            newSubject.setName(subjectDto.getName());
        }
        if (!subjectDto.getDescription().isEmpty()) {
            newSubject.setDescription(subjectDto.getDescription());
        }
        if (subjectDto.getClassroomDto() != null) {
            Classroom classroom = classroomRepository.findByName(subjectDto.getClassroomDto().getName()).orElseThrow();
            newSubject.setClassroom(classroom);
        }
        if (subjectDto.getUserDto() != null) {
            User user = userRepository.findUserByUsername(subjectDto.getUserDto().getUsername());
            newSubject.setUser(user);
        }
        subjectRepository.saveAndFlush(newSubject);
    }

    @Transactional
    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    public boolean existsByName(String subjectName) {
        return subjectRepository.existsByNameIgnoreCase(subjectName);
    }
}