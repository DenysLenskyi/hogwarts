package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.form.ProfessorForm;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    UserDto findById(Long id);

    User findByUsername(String username);

    boolean existsById(Long userId);

    List<User> findAllProfessorsWithNoSubject();

    List<User> findAllStudents();

    List<User> findAllProfessorsAndAdmins();

    boolean existsByUsername(String username);

    void createStudent(UserDto userDto);
    void createProfessor(ProfessorForm professorForm);

    void deleteById(Long id);

    void updateStudent(UserDto userDto);

    ProfessorForm createProfessorFormDto(Long id);

    void updateProfessor(ProfessorForm professorForm);
}
