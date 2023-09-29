package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> findAll();

    List<SubjectDto> findAllDto();

    SubjectDto findById(Long subjectId);

    Subject findSubjectById(Long id);

    SubjectDto findByName(String subjectName);

    void updateSubject(SubjectDto subjectDto);

    boolean doesSubjectExistById(Long subjectId);

    void createNewSubject(SubjectDto subjectDto);

    void deleteSubjectById(Long id);

    boolean existsByName(String subjectName);

    List<Subject> findAllSubjectsWithNoProfessor();
}