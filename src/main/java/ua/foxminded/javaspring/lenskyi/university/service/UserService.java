package ua.foxminded.javaspring.lenskyi.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public void addRole(User user, String roleName) {
        user.getRoles().add(roleRepository.findRoleByName(roleName).orElseThrow());
        userRepository.save(user);
    }

    public void removeRole(User user, String roleName) {
        user.getRoles().remove(roleRepository.findRoleByName(roleName).orElseThrow());
        userRepository.save(user);
    }
}
