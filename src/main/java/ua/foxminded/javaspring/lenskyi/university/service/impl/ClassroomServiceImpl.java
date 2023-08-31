package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.ClassroomEntityClassroomDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomEntityClassroomDtoMapper mapper;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ClassroomEntityClassroomDtoMapper mapper) {
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    public Classroom findByName(String classroomName) {
        return classroomRepository.findByName(classroomName).orElseThrow();
    }

    public List<Classroom> findAllFreeClassrooms() {
        return classroomRepository.findAllBySubjectIsNull();
    }
}
