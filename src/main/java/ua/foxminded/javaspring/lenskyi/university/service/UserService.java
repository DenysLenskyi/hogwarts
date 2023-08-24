package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findById(Long id);

    User findUserByFirstName(String firstName);

    User findUserByLastName(String lastName);

    User findUserByFirstAndLastName(String firstName, String lastName);

    User findUserByUsername(String username);

    void addRole(User user, String roleName);

    void removeRole(User user, String roleName);

    void updateRolesFromArray(User user, List<String> newRolesArray) throws Exception;

    boolean doesUserExistById(Long userId);

    UserDto getUserDtoByUserId(Long id);

    List<User> findAllProfessorsWithNoSubject();
}
