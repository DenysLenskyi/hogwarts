package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

@Mapper(componentModel = "spring")
public interface SubjectEntitySubjectDtoMapper {

    SubjectDto subjectEntityToSubjectDto(Subject subject);

    Subject subjectDtoToSubjectEntity(SubjectDto subjectDto);
}