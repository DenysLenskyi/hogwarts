package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

@Mapper(componentModel = "spring", uses = {SubjectEntitySubjectDtoMapper.class})
public interface ClassroomEntityClassroomDtoMapper {

    @Mapping(target = "subjectDto.name", source = "subject.name")
    @Mapping(target = "subjectDto.id", source = "subject.id")
    @Mapping(target = "subjectDto.description", source = "subject.description")
    @Mapping(target = "subjectDto.userDto", source = "subject.user", ignore = true)
    @Mapping(target = "subjectDto.classroomDto", source = "subject.classroom", ignore = true)
    @Mapping(target = "subjectDto.lessonsDto", source = "subject.lessons", ignore = true)
    ClassroomDto classroomEntityToClassroomDto(Classroom classroom);
    Classroom classroomDtoToClassroomEntity(ClassroomDto classroomDto);
}