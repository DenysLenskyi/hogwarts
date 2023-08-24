package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

import java.util.List;

public interface ClassroomService {

    Classroom findByName(String classroomName);

    List<Classroom> findAllFreeClassrooms();
}
