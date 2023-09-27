package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonTimeDto;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;

@Mapper(componentModel = "spring")
public interface LessonTimeEntityLessonTimeDtoMapper {

    LessonTimeDto lessonTimeEntityToLessonTimeDto(LessonTime lessonTime);
}