package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    List<Subject> findAll();

    SubjectDto findById(Long subjectId);

    SubjectDto findByName(String subjectName);

    void updateSubjectFromSubjectDto(SubjectDto subjectDto);

    boolean doesSubjectExistById(Long subjectId);

    void createNewSubjectFromSubjectDto(SubjectDto subjectDto);

    void deleteSubjectById(Long id);

    boolean existsByName(String subjectName);
}