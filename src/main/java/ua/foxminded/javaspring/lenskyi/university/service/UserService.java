package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.form.ProfessorForm;
import ua.foxminded.javaspring.lenskyi.university.exception.NotUniqueUsernameException;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;

public interface UserService {

    UserDto findById(Long id);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    boolean existsById(Long userId);

    List<UserDto> findAllProfessorWithNoSubject();

    List<UserDto> findAllStudent();
    void createStudent(UserDto userDto) throws NotUniqueUsernameException;
    void deleteById(Long id);
    void updateStudent(UserDto userDto);

    List<UserDto> findAllProfessorAndAdmin();

    void createProfessor(ProfessorForm professorForm) throws NotUniqueUsernameException;

    ProfessorForm createProfessorFormDto(Long id);

    void updateProfessor(ProfessorForm professorForm);
}