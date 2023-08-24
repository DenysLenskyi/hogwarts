package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByName(String classroomName);

    @Query("select c from Classroom c left join c.subject where c.subject is null")
    List<Classroom> findAllFreeClassrooms();
}