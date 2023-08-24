package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.SubjectEntitySubjectDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    SubjectRepository subjectRepository;
    SubjectEntitySubjectDtoMapper mapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectEntitySubjectDtoMapper mapper) {
        this.subjectRepository = subjectRepository;
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
            subjectToUpdate.setClassroom(subjectDto.getClassroom());
        }
        if (subjectDto.getUser() != null) {
            subjectToUpdate.setUser(subjectDto.getUser());
        }
        subjectRepository.save(subjectToUpdate);
        subjectRepository.flush();
    }

    public boolean doesSubjectExistById(Long subjectId) {
        return subjectRepository.existsById(subjectId);
    }
}