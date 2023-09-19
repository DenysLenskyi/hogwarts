package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.form.ProfessorForm;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.UserEntityUserDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.SubjectRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final String STUDENT_ROLE_NAME = "student";
    private static final String PROFESSOR_ROLE_NAME = "professor";
    private static final String ADMIN_ROLE_NAME = "admin";
    private static final String EMPTY_STRING = "";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final UserEntityUserDtoMapper userDtoMapper;
    private final GroupEntityGroupDtoMapper groupDtoMapper;
    private PasswordEncoder passwordEncoder;
    private Pattern bcryptPattern = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           GroupRepository groupRepository, SubjectRepository subjectRepository,
                           UserEntityUserDtoMapper userDtoMapper, GroupEntityGroupDtoMapper groupDtoMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.userDtoMapper = userDtoMapper;
        this.groupDtoMapper = groupDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        UserDto userDto = userDtoMapper.userEntityToUserDto(user);
        if (user.getGroup() != null) {
            userDto.setGroupDto(groupDtoMapper.groupEntityToGroupDto(groupRepository.findByName(
                    user.getGroup().getName()).orElseThrow(IllegalArgumentException::new)));
        }
        return userDto;
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
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

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    public UserDto getUserDtoByUserId(Long id) {
        return userDtoMapper.userEntityToUserDto(userRepository.findById(id).orElseThrow());
    }

    public List<User> findAllProfessorsWithNoSubject() {
        return userRepository.findAllBySubjectIsNullAndRolesContains(
                roleRepository.findRoleByName(PROFESSOR_ROLE_NAME).orElseThrow(IllegalArgumentException::new));
    }

    public List<User> findAllStudents() {
        Role studentRole = roleRepository.findRoleByName(STUDENT_ROLE_NAME).orElseThrow(IllegalArgumentException::new);
        return userRepository.findAllByRolesContains(studentRole);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void createStudentFromUserDto(@Valid UserDto userDto) {
        User newStudent = new User();
        newStudent.setFirstName(userDto.getFirstName());
        if (userDto.getLastName().isEmpty() || userDto.getLastName().isBlank()) {
            newStudent.setLastName(EMPTY_STRING);
        } else {
            newStudent.setLastName(userDto.getLastName());
        }
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
    public void updateStudentFromUserDto(@Valid UserDto userDto) {
        User userToUpdate = userRepository.findById(userDto.getId()).orElseThrow(IllegalArgumentException::new);
        userToUpdate.setFirstName(userDto.getFirstName());
        if (userDto.getLastName().isEmpty() || userDto.getLastName().isBlank()) {
            userToUpdate.setLastName(EMPTY_STRING);
        } else {
            userToUpdate.setLastName(userDto.getLastName());
        }
        userToUpdate.setUsername(userDto.getUsername());
        if (bcryptPattern.matcher(userDto.getPassword()).matches()) {
            userToUpdate.setPassword(userDto.getPassword());
        } else {
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userToUpdate.setGroup(groupRepository.findByName(
                userDto.getGroupDto().getName()).orElseThrow(IllegalArgumentException::new));
        userRepository.saveAndFlush(userToUpdate);
    }

    public List<User> findAllProfessorsAndAdmins() {
        Role professorRole = roleRepository.findRoleByName(PROFESSOR_ROLE_NAME).orElseThrow(IllegalArgumentException::new);
        Role adminRole = roleRepository.findRoleByName(ADMIN_ROLE_NAME).orElseThrow(IllegalArgumentException::new);
        List<User> professors = userRepository.findAllByRolesContains(professorRole);
        List<User> admins = userRepository.findAllByRolesContains(adminRole);
        Set<User> professorsAndAdmins = new HashSet<>();
        professors.forEach(professorsAndAdmins::add);
        admins.forEach(professorsAndAdmins::add);
        return professorsAndAdmins.stream().toList();
    }

    @Transactional
    public void createProfessorFromProfessorForm(@Valid ProfessorForm professorForm) {
        User newProfessor = new User();
        newProfessor.setFirstName(professorForm.getFirstName());
        if (professorForm.getLastName().isEmpty() || professorForm.getLastName().isBlank()) {
            newProfessor.setLastName(EMPTY_STRING);
        } else {
            newProfessor.setLastName(professorForm.getLastName());
        }
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

    public ProfessorForm createAndFillProfessorFormByUserId(Long id) {
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
        professorForm.setAdmin(user.getRoles().contains(roleRepository.findRoleByName(ADMIN_ROLE_NAME).orElseThrow()));
        return professorForm;
    }

    @Transactional
    public void updateProfessorFromProfessorForm(@Valid ProfessorForm professorForm) {
        User userToUpdate = userRepository.findById(professorForm.getId()).orElseThrow(IllegalArgumentException::new);
        userToUpdate.setFirstName(professorForm.getFirstName());
        if (professorForm.getLastName().isEmpty() || professorForm.getLastName().isBlank()) {
            userToUpdate.setLastName(EMPTY_STRING);
        } else {
            userToUpdate.setLastName(professorForm.getLastName());
        }
        userToUpdate.setUsername(professorForm.getUsername());
        if (bcryptPattern.matcher(professorForm.getPassword()).matches()) {
            userToUpdate.setPassword(professorForm.getPassword());
        } else {
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