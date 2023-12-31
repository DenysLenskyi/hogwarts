package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends BaseRepository<Classroom, Long> {

    Optional<Classroom> findByName(String classroomName);

    List<Classroom> findAllBySubjectIsNull();
}