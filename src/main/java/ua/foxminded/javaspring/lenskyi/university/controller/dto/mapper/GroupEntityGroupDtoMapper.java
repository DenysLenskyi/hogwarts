package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;

@Mapper(componentModel = "spring")
public interface GroupEntityGroupDtoMapper {

    @Mapping(target = "numStudentsInGroup", expression = "java(group.getUsers() != null ? group.getUsers().size() : 0)")
    GroupDto groupEntityToGroupDto(Group group);
    Group groupDtoToGroupEntity(GroupDto groupDto);
}