package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByFirstName(String firstName);
    User findUserByLastName(String lastName);
    User findUserByFirstNameAndLastName(String firstName, String lastName);
    User findUserByUsername(String username);
    boolean existsById(Long userId);
}