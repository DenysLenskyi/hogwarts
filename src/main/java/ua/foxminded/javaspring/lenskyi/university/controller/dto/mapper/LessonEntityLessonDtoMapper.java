package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;

@Mapper(componentModel = "spring", uses = {LessonTimeEntityLessonTimeDtoMapper.class})
public interface LessonEntityLessonDtoMapper {

    @Mapping(target = "lessonTimeDto", source = "lessonTime")
    @Mapping(target = "groupDto.name", source = "group.name")
    @Mapping(target = "groupDto.users", ignore = true)
    @Mapping(target = "groupDto.lessons", ignore = true)
    @Mapping(target = "subjectDto.name", source = "subject.name")
    @Mapping(target = "subjectDto.lessonsDto", ignore = true)
    @Mapping(target = "subjectDto.userDto.firstName", source = "subject.user.firstName")
    @Mapping(target = "subjectDto.userDto.lastName", source = "subject.user.lastName")
    @Mapping(target = "subjectDto.classroomDto.name", source = "subject.classroom.name")
    LessonDto lessonEntityToLessonDto(Lesson lesson);

    Lesson lessonDtoToLessonEntity(LessonDto lessonDto);
}