package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

import java.util.List;

public interface ClassroomService {

    List<Classroom> findAll();
    List<Classroom> findAllFreeClassrooms();
}