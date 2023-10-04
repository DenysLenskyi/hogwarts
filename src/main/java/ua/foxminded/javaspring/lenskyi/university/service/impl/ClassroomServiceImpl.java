package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.ClassroomEntityClassroomDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

import java.util.Comparator;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomEntityClassroomDtoMapper mapper;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ClassroomEntityClassroomDtoMapper mapper) {
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ClassroomDto> findAll() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream()
                .map(mapper::classroomEntityToClassroomDto)
                .sorted(Comparator.comparing(ClassroomDto::getId))
                .toList();
    }

    @Override
    public List<Classroom> findAllFreeClassrooms() {
        return classroomRepository.findAllBySubjectIsNull();
    }

    @Override
    public boolean existsByName(String classroomName) {
        return classroomRepository.existsByName(classroomName);
    }

    @Override
    public boolean existsById(Long id) {
        return classroomRepository.existsById(id);
    }

    @Override
    public void deleteClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow();
        if (classroom.getSubject() != null) {
            classroom.getSubject().setClassroom(null);
        }
        classroomRepository.deleteById(id);
    }

    @Override
    public void createClassroom(ClassroomDto classroomDto) {
        classroomRepository.saveAndFlush(mapper.classroomDtoToClassroomEntity(classroomDto));
    }

    @Override
    public ClassroomDto findById(Long id) {
        return mapper.classroomEntityToClassroomDto(
                classroomRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void updateClassroom(ClassroomDto classroomDto) {
        Classroom classroomToUpdate = classroomRepository.findById(classroomDto.getId()).orElseThrow();
        classroomToUpdate.setName(classroomDto.getName());
        classroomToUpdate.setDescription(classroomDto.getDescription());
        classroomRepository.saveAndFlush(classroomToUpdate);
    }
}