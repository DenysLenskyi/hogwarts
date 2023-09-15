package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.RoleDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.RoleEntityRoleDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleEntityRoleDtoMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleEntityRoleDtoMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName).orElseThrow();
    }

    public RoleDto findByName(String roleName) {
        return mapper.roleEntityToRoleDto(findRoleByName(roleName));
    }
}