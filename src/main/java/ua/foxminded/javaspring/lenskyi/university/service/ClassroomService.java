package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

import java.util.List;

public interface ClassroomService {

    List<ClassroomDto> findAll();

    List<Classroom> findAllFreeClassrooms();

    boolean existsByName(String classroomName);

    boolean existsById(Long id);

    void deleteClassroomById(Long id);

    void createClassroom(ClassroomDto classroomDto);

    ClassroomDto findById(Long id);

    void updateClassroom(ClassroomDto classroomDto);
}