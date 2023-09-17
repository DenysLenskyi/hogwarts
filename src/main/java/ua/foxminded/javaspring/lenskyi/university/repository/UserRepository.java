package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByFirstName(String firstName);

    User findUserByLastName(String lastName);

    User findUserByFirstNameAndLastName(String firstName, String lastName);

    User findUserByUsername(String username);

    boolean existsById(Long userId);

    List<User> findAllBySubjectIsNullAndRolesContains(Role role);

    long countAllByGroupName(String groupName);

    List<User> findAllByGroupName(String groupName);

    List<User> findAllByRolesContains(Role role);

    boolean existsByUsername(String username);

    @Modifying
    @Query("update User u set u.firstName = :firstName, u.lastName = :lastName, u.username = :username," +
            " u.password = :password, u.roles = :roles where u.id = :id")
    void updateProfessor(@Param(value = "id") Long id, @Param(value = "firstName") String firstName,
                         @Param(value = "lastName") String lastName, @Param(value = "username") String username,
                         @Param(value = "password") String password,
                         @Param(value = "roles") Set<Role> roles);
}