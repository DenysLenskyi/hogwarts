package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    List<Subject> findAll();

    Optional<Subject> findById(Long subjectId);

    SubjectDto findSubjectDtoById(Long subjectId);

    Optional<Subject> findByName(String subjectName);

    SubjectDto findSubjectDtoByName(String subjectName);

    void updateSubjectFromSubjectDto(SubjectDto subjectDto);

    boolean doesSubjectExistById(Long subjectId);
}