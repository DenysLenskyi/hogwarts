package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;

import java.util.Optional;

@Repository
public interface LessonTimeRepository extends JpaRepository<LessonTime, Long> {

    Optional<LessonTime> findById(Long id);
}
