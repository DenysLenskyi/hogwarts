package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends BaseRepository<Group, Long> {

    Optional<Group> findByName(String groupName);
}