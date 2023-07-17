package ua.foxminded.javaspring.lenskyi.university;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class DBTest {

    @Test
    void assignStudentToSubjectTest() {
//        assertThrows(SQLException.class, () -> {
//            subjectTableViolation();
//        });
        subjectTableViolation();
    }

    @Sql("""
            INSERT INTO HOGWARTS.USER (GROUP_ID, FIRST_NAME, LAST_NAME) VALUES
            (null, aStudentWhoWantsToTakeSubject, test);
            INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
            SELECT u.ID, r.ID
            FROM HOGWARTS.USER u
                     JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'aStudentWhoWantsToTakeSubject' and r.NAME = 'student');
            INSERT INTO HOGWARTS.SUBJECT (NAME, DESCRIPTION, PROFESSOR_ID, CLASSROOM_ID)
            SELECT ('testSubject', 'testDescr',
            SELECT u.ID FROM HOGWARTS.USER u where u.FIRST_NAME = 'aStudentWhoWantsToTakeSubject',
            null);
            """)
    void subjectTableViolation() {
    }
}