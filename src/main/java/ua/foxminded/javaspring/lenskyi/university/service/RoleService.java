package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.RoleDto;
import ua.foxminded.javaspring.lenskyi.university.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleDto> findAll();

    Role findRoleByName(String roleName);

    RoleDto findByName(String roleName);
}