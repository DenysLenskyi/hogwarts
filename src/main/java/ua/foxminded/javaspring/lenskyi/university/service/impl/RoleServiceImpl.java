package ua.foxminded.javaspring.lenskyi.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.RoleDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.RoleEntityRoleDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleEntityRoleDtoMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleEntityRoleDtoMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(mapper::roleEntityToRoleDto)
                .toList();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName).orElseThrow();
    }

    @Override
    public RoleDto findByName(String roleName) {
        return mapper.roleEntityToRoleDto(findRoleByName(roleName));
    }
}