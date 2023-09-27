package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.LessonDto;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;

@Mapper(componentModel = "spring", uses = {LessonTimeEntityLessonTimeDtoMapper.class})
public interface LessonEntityLessonDtoMapper {

    @Mapping(target = "lessonTimeDto", source = "lessonTime")
    LessonDto lessonEntityToLessonDto(Lesson lesson);

    Lesson lessonDtoToLessonEntity(LessonDto lessonDto);
}