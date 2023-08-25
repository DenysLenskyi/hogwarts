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
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    SubjectRepository subjectRepository;
    ClassroomRepository classroomRepository;
    UserRepository userRepository;
    SubjectEntitySubjectDtoMapper mapper;

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

    public Optional<Subject> findById(Long subjectId) {
        return subjectRepository.findById(subjectId);
    }

    public SubjectDto findSubjectDtoById(Long subjectId) {
        return mapper.subjectEntityToSubjectDto(subjectRepository.findById(subjectId).orElseThrow());
    }

    public Optional<Subject> findByName(String subjectName) {
        return subjectRepository.findSubjectByName(subjectName);
    }

    public SubjectDto findSubjectDtoByName(String subjectName) {
        return mapper.subjectEntityToSubjectDto(subjectRepository.findSubjectByName(subjectName).orElseThrow());
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
        subjectRepository.save(subjectToUpdate);
        subjectRepository.flush();
    }

    public boolean doesSubjectExistById(Long subjectId) {
        return subjectRepository.existsById(subjectId);
    }
}