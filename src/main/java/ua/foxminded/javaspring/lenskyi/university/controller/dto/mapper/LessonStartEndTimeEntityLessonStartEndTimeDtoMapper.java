package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonStartEndTimeDto;
import ua.foxminded.javaspring.lenskyi.university.model.LessonStartEndTime;

@Mapper(componentModel = "spring")
public interface LessonStartEndTimeEntityLessonStartEndTimeDtoMapper {

    LessonStartEndTimeDto lessonStartEndTimeEntityToLessonStartEndTimeDto(LessonStartEndTime lessonStartEndTime);
    LessonStartEndTime lessonStartEndTimeDtoToLessonStartEndTimeEntity(LessonStartEndTimeDto lessonStartEndTimeDto);
}