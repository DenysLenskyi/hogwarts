package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

@Mapper(componentModel = "spring")
public interface SubjectEntitySubjectDtoMapper {

    @Mapping(target = "userDto.firstName", source = "user.firstName")
    @Mapping(target = "userDto.lastName", source = "user.lastName")
    @Mapping(target = "classroomDto.name", source = "classroom.name")
    SubjectDto subjectEntityToSubjectDto(Subject subject);

    Subject subjectDtoToSubjectEntity(SubjectDto subjectDto);
}