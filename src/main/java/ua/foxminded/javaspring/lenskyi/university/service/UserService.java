package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findById(Long id);

    User findUserByUsername(String username);

    void updateRolesFromArray(User user, List<String> newRolesArray) throws Exception;

    boolean doesUserExistById(Long userId);

    UserDto getUserDtoByUserId(Long id);

    List<User> findAllProfessorsWithNoSubject();

    List<User> findAllStudents();

    boolean existsByUsername(String username);

    void createStudentFromUserDto(UserDto userDto);
}
