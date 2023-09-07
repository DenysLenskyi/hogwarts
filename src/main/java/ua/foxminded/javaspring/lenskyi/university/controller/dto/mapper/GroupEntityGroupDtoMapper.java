package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;

@Mapper(componentModel = "spring")
public interface GroupEntityGroupDtoMapper {

    GroupDto groupEntityToGroupDto(Group group);
    Group groupDtoToGroupEntity(GroupDto groupDto);
}