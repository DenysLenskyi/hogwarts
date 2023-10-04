package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.LessonTime;

@Repository
public interface LessonTimeRepository extends JpaRepository<LessonTime, Long> {

}
