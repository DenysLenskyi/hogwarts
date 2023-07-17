package ua.foxminded.javaspring.lenskyi.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;
import ua.foxminded.javaspring.lenskyi.university.model.User;


import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class SubjectRepositoryTest {

    private final static String expectedNameForPotionSubject = "Potions";

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findAllInitialSubjectTest() {
        assertEquals(12, subjectRepository.findAll().size());
    }

    @Test
    void professorSubjectCorrelationCorrectnessTest() {
        final String expectedProfessorLastNameForPotionsSubject = "Snape";
        Subject potions = subjectRepository.findSubjectByName(expectedNameForPotionSubject).orElseThrow();
        assertEquals(expectedProfessorLastNameForPotionsSubject, potions.getUser().getLastName());
    }

    @Test
    void subjectClassroomCorrelationCorrectnessTest() {
        Subject potions = subjectRepository.findSubjectByName(expectedNameForPotionSubject).orElseThrow();
        assertEquals("Potions Classroom", potions.getClassroom().getName());
    }
}