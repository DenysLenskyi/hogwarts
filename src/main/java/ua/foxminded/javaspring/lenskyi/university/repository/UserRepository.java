package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.exception.NotUniqueUsernameException;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByFirstName(String firstName);

    User findUserByLastName(String lastName);

    User findUserByFirstNameAndLastName(String firstName, String lastName);

    User findUserByUsername(String username);

    List<User> findAllBySubjectIsNullAndRolesContains(Role role);

    long countAllByGroupName(String groupName);

    List<User> findAllByGroupName(String groupName);

    List<User> findAllByRolesContains(Role role);

    boolean existsByUsername(String username);

    List<User> findAllByRolesIsIn(List<Role> roles);
}