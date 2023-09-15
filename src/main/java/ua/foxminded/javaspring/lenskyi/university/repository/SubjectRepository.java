package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findSubjectByName(String subjectName);

    boolean existsById(Long subjectId);

    boolean existsByNameIgnoreCase(String subjectName);

    List<Subject> findAllByUserIsNull();
}