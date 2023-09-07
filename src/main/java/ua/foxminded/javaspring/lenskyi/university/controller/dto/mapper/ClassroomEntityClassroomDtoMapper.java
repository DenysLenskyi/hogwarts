package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

@Mapper(componentModel = "spring")
public interface ClassroomEntityClassroomDtoMapper {

    ClassroomDto classroomEntityToClassroomDto(Classroom classroom);
    Classroom classroomDtoToClassroomEntity(ClassroomDto classroomDto);
}