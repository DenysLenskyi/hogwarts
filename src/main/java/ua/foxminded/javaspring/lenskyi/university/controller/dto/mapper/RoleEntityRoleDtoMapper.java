package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.RoleDto;
import ua.foxminded.javaspring.lenskyi.university.model.Role;

@Mapper(componentModel = "spring")
public interface RoleEntityRoleDtoMapper {

    RoleDto roleEntityToRoleDto(Role role);
    Role roleDtoToRoleEntity(RoleDto roleDto);
}