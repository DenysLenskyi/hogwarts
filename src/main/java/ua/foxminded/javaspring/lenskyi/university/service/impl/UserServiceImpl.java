package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.form.ProfessorForm;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.UserEntityUserDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.exception.NotUniqueUsernameException;
import ua.foxminded.javaspring.lenskyi.university.model.*;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final String STUDENT_ROLE_NAME = "student";
    private static final String PROFESSOR_ROLE_NAME = "professor";
    private static final String ADMIN_ROLE_NAME = "admin";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectService subjectService;
    private final UserEntityUserDtoMapper userDtoMapper;
    private final GroupEntityGroupDtoMapper groupDtoMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           GroupRepository groupRepository, SubjectRepository subjectRepository,
                           UserEntityUserDtoMapper userDtoMapper, GroupEntityGroupDtoMapper groupDtoMapper,
                           PasswordEncoder passwordEncoder, SubjectService subjectService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.userDtoMapper = userDtoMapper;
        this.groupDtoMapper = groupDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.subjectService = subjectService;
    }


    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return userDtoMapper.userEntityToUserDto(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    public List<UserDto> findAllProfessorWithNoSubject() {
        Role professor = roleRepository.findRoleByName(PROFESSOR_ROLE_NAME).orElseThrow(IllegalArgumentException::new);
        List<User> professors = userRepository.findAllBySubjectIsNullAndRolesContains(professor);
        return professors.stream()
                .map(userDtoMapper::userEntityToUserDto)
                .sorted(Comparator.comparing(UserDto::getId))
                .toList();
    }

    public List<UserDto> findAllStudent() {
        Role studentRole = roleRepository.findRoleByName(STUDENT_ROLE_NAME).orElseThrow(IllegalArgumentException::new);
        List<User> students = userRepository.findAllByRolesContains(studentRole);
        return students.stream()
                .map(userDtoMapper::userEntityToUserDto)
                .sorted(Comparator.comparing(UserDto::getId))
                .toList();
    }

    @Transactional
    public void createStudent(@Valid UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            try {
                throw new NotUniqueUsernameException(userDto.getUsername());
            } catch (NotUniqueUsernameException e) {
                throw new RuntimeException(e);
            }
        }
        User newStudent = new User();
        newStudent.setFirstName(userDto.getFirstName());
        newStudent.setLastName(userDto.getLastName());
        newStudent.setUsername(userDto.getUsername());
        newStudent.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role studentRole = roleRepository.findRoleByName(STUDENT_ROLE_NAME).orElseThrow(IllegalArgumentException::new);
        newStudent.setRoles(Set.of(studentRole));
        newStudent.setGroup(groupRepository.findByName(
                userDto.getGroupDto().getName()).orElseThrow(IllegalArgumentException::new));
        userRepository.saveAndFlush(newStudent);
    }

    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        if (user.getSubject() != null) {
            user.getSubject().setUser(null);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(@Valid UserDto userDto) {
        User userToUpdate = userRepository.findById(userDto.getId()).orElseThrow(IllegalArgumentException::new);
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setUsername(userDto.getUsername());
        if (!userDto.getPassword().isEmpty() && !userDto.getPassword().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userToUpdate.setGroup(groupRepository.findByName(
                userDto.getGroupDto().getName()).orElseThrow(IllegalArgumentException::new));
        userRepository.saveAndFlush(userToUpdate);
    }

    public List<UserDto> findAllProfessorAndAdmin() {
        List<User> professorsAndAdmins = userRepository.findAllByRolesIsIn(
                roleRepository.findAllByNameIsIn(List.of(ADMIN_ROLE_NAME, PROFESSOR_ROLE_NAME)));
        return professorsAndAdmins.stream()
                .map(userDtoMapper::userEntityToUserDto)
                .sorted(Comparator.comparing(UserDto::getId))
                .toList();
    }

    @Transactional
    public void createProfessor(@Valid ProfessorForm professorForm) {
        if (userRepository.existsByUsername(professorForm.getUsername())) {
            try {
                throw new NotUniqueUsernameException(professorForm.getUsername());
            } catch (NotUniqueUsernameException e) {
                throw new RuntimeException(e);
            }
        }
        User newProfessor = new User();
        newProfessor.setFirstName(professorForm.getFirstName());
        newProfessor.setLastName(professorForm.getLastName());
        newProfessor.setUsername(professorForm.getUsername());
        newProfessor.setPassword(passwordEncoder.encode(professorForm.getPassword()));
        Subject subject = subjectRepository.findSubjectByName(professorForm.getSubjectName()).orElse(null);
        newProfessor.setSubject(subject);
        Set<Role> rolesForNewProfessor = new HashSet<>();
        rolesForNewProfessor.add(
                roleRepository.findRoleByName(PROFESSOR_ROLE_NAME).orElseThrow(IllegalArgumentException::new));
        if (professorForm.isAdmin()) {
            rolesForNewProfessor.add(roleRepository.findRoleByName(ADMIN_ROLE_NAME).orElseThrow());
        }
        newProfessor.setRoles(rolesForNewProfessor);
        userRepository.saveAndFlush(newProfessor);
        if (subject != null) {
            subject.setUser(newProfessor);
            subjectRepository.saveAndFlush(subject);
        }
    }

    public ProfessorForm createProfessorFormDto(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        ProfessorForm professorForm = new ProfessorForm();
        professorForm.setId(user.getId());
        professorForm.setFirstName(user.getFirstName());
        professorForm.setLastName(user.getLastName());
        professorForm.setUsername(user.getUsername());
        professorForm.setPassword(user.getPassword());
        if (user.getSubject() != null) {
            professorForm.setSubjectName(user.getSubject().getName());
        }
        boolean isAdmin = user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(ADMIN_ROLE_NAME::equals);
        professorForm.setAdmin(isAdmin);
        return professorForm;
    }

    @Transactional
    public void updateProfessor(@Valid ProfessorForm professorForm) {
        User userToUpdate = userRepository.findById(professorForm.getId()).orElseThrow(IllegalArgumentException::new);
        userToUpdate.setFirstName(professorForm.getFirstName());
        userToUpdate.setLastName(professorForm.getLastName());
        userToUpdate.setUsername(professorForm.getUsername());
        if (!professorForm.getPassword().isEmpty() && !professorForm.getPassword().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(professorForm.getPassword()));
        }
        if (professorForm.isAdmin()) {
            userToUpdate.getRoles().add(roleRepository.findRoleByName(ADMIN_ROLE_NAME).orElseThrow());
        } else {
            userToUpdate.getRoles().remove(roleRepository.findRoleByName(ADMIN_ROLE_NAME).orElseThrow());
        }
        userRepository.saveAndFlush(userToUpdate);
    }
}