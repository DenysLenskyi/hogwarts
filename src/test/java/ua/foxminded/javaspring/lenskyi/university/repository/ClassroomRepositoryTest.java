package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class ClassroomRepositoryTest {

    private static final String expectedNameForPotionsClassroom = "Potions Classroom";
    private static final String expectedNameForForbiddenForest = "Forbidden Forest";
    private static final String expectedNameForHagridsHut = "Hagrid's Hut";

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void entityCorrectnessTest() {
        Classroom potionsClassroom = classroomRepository.findByName(expectedNameForPotionsClassroom).orElseThrow();
        Classroom forest = classroomRepository.findByName(expectedNameForForbiddenForest).orElseThrow();
        Classroom hagridHut = classroomRepository.findByName(expectedNameForHagridsHut).orElseThrow();
        assertTrue(potionsClassroom.getName().equals(expectedNameForPotionsClassroom));
        assertTrue(forest.getName().equals(expectedNameForForbiddenForest));
        assertTrue(hagridHut.getName().equals(expectedNameForHagridsHut));
        assertTrue(hagridHut.getDescription().length() > 20);
    }

    @Test
    void classroomWithNullSubjectTest() {
        Classroom classroom = classroomRepository.findByName("Dungeon Five").orElseThrow();
        assertTrue(classroom.getSubject() == null);
    }

    @Test
    void findAllFreeClassroomsTest() {
        List<Classroom> allClassrooms = classroomRepository.findAll();
        List<Classroom> allFreeClassrooms = classroomRepository.findAllBySubjectIsNull();
        List<Subject> allSubjects = subjectRepository.findAll();
        assertEquals(allFreeClassrooms.size(), allClassrooms.size() - allSubjects.size());
    }
}