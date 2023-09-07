package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;

@Mapper(componentModel = "spring")
public interface LessonEntityLessonDtoMapper {

    LessonDto lessonEntityToLessonDto(Lesson lesson);
    Lesson lessonDtoToLessonEntity(LessonDto lessonDto);
}