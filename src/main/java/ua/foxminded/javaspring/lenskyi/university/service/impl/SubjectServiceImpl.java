package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.SubjectEntitySubjectDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final SubjectEntitySubjectDtoMapper mapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ClassroomRepository classroomRepository,
                              UserRepository userRepository, SubjectEntitySubjectDtoMapper mapper) {
        this.subjectRepository = subjectRepository;
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public SubjectDto findById(Long subjectId) {
        return mapper.subjectEntityToSubjectDto(subjectRepository.findById(subjectId)
                .orElseThrow(NoSuchElementException::new));
    }

    public SubjectDto findByName(String subjectName) {
        return mapper.subjectEntityToSubjectDto(subjectRepository.findSubjectByName(subjectName)
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
        if (subjectDto.getClassroom() != null) {
            Classroom classroom = classroomRepository.findByName(subjectDto.getClassroom().getName()).orElseThrow();
            subjectToUpdate.setClassroom(classroom);
        }
        if (subjectDto.getUser() != null) {
            User user = userRepository.findUserByUsername(subjectDto.getUser().getUsername());
            subjectToUpdate.setUser(user);
        }
        subjectRepository.saveAndFlush(subjectToUpdate);
    }

    public boolean doesSubjectExistById(Long subjectId) {
        return subjectRepository.existsById(subjectId);
    }

    public void createNewSubjectFromSubjectDto(SubjectDto subjectDto) {
        Subject newSubject = new Subject();
        if (!subjectDto.getName().isEmpty()) {
            newSubject.setName(subjectDto.getName());
        }
        if (!subjectDto.getDescription().isEmpty()) {
            newSubject.setDescription(subjectDto.getDescription());
        }
        if (subjectDto.getClassroom() != null) {
            Classroom classroom = classroomRepository.findByName(subjectDto.getClassroom().getName()).orElseThrow();
            newSubject.setClassroom(classroom);
        }
        if (subjectDto.getUser() != null) {
            User user = userRepository.findUserByUsername(subjectDto.getUser().getUsername());
            newSubject.setUser(user);
        }
        subjectRepository.saveAndFlush(newSubject);
    }

    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }
}