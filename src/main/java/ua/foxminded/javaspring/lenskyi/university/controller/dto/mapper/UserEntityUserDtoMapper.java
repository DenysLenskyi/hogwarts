package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.User;

@Mapper(componentModel = "spring", uses = {GroupEntityGroupDtoMapper.class, SubjectEntitySubjectDtoMapper.class})
public interface UserEntityUserDtoMapper {

    @Mapping(target = "groupDto.id", source = "group.id")
    @Mapping(target = "groupDto.name", source = "group.name")
    @Mapping(target = "groupDto.numStudentsInGroup", ignore = true)
    @Mapping(target = "groupDto.users", ignore = true)
    @Mapping(target = "groupDto.lessons", ignore = true)
    @Mapping(target = "subjectDto.id", source = "subject.id")
    @Mapping(target = "subjectDto.name", source = "subject.name")
    @Mapping(target = "subjectDto.description", source = "subject.description")
    @Mapping(target = "subjectDto.userDto", ignore = true)
    @Mapping(target = "subjectDto.classroomDto", ignore = true)
    @Mapping(target = "subjectDto.lessonsDto", ignore = true)
    UserDto userEntityToUserDto(User user);

    User userDtoToUserEntity(UserDto userDto);
}