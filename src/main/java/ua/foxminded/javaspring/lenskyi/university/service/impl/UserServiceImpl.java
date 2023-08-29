package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.UserEntityUserDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserEntityUserDtoMapper mapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserEntityUserDtoMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User findUserByFirstName(String firstName) {
        return userRepository.findUserByFirstName(firstName);
    }

    public User findUserByLastName(String lastName) {
        return userRepository.findUserByLastName(lastName);
    }

    public User findUserByFirstAndLastName(String firstName, String lastName) {
        return userRepository.findUserByFirstNameAndLastName(firstName, lastName);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public void addRole(User user, String roleName) {
        user.getRoles().add(roleRepository.findRoleByName(roleName).orElseThrow());
        userRepository.save(user);
    }

    @Transactional
    public void removeRole(User user, String roleName) {
        user.getRoles().remove(roleRepository.findRoleByName(roleName).orElseThrow());
        userRepository.save(user);
    }

    @Transactional
    public void updateRolesFromArray(User user, List<String> newRolesArray) throws Exception {
        if (!newRolesArray.isEmpty()) {
            Set<Role> updatedRoles = new HashSet<>();
            newRolesArray.forEach(roleName ->
                updatedRoles.add(roleRepository.findRoleByName(roleName).orElseThrow())
            );
            user.setRoles(updatedRoles);
            userRepository.save(user);
            userRepository.flush();
        }
    }

    public boolean doesUserExistById(Long userId) {
        return userRepository.existsById(userId);
    }

    public UserDto getUserDtoByUserId(Long id) {
        return mapper.userEntityToUserDto(userRepository.findById(id).orElseThrow());
    }

    public List<User> findAllProfessorsWithNoSubject() {
        return userRepository.findAllBySubjectIsNullAndRolesContains(
                roleRepository.findRoleByName("professor").orElseThrow());
    }
}