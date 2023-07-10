CREATE SCHEMA IF NOT EXISTS HOGWARTS;
SET search_path TO HOGWARTS, public;

CREATE TABLE IF NOT EXISTS HOGWARTS.ROLE
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS HOGWARTS.GROUP
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS HOGWARTS.LESSON_START_END_TIME
(
    ID         BIGINT PRIMARY KEY,
    START_TIME TIME,
    END_TIME   TIME
);

CREATE TABLE IF NOT EXISTS HOGWARTS.CLASSROOM
(
    ID          BIGSERIAL PRIMARY KEY,
    NAME        TEXT UNIQUE NOT NULL,
    DESCRIPTION TEXT
);

CREATE TABLE IF NOT EXISTS HOGWARTS.SUBJECT
(
    ID           BIGSERIAL PRIMARY KEY,
    NAME         TEXT UNIQUE NOT NULL,
    DESCRIPTION  TEXT,
    PROFESSOR_ID BIGINT,
    CLASSROOM_ID BIGINT
);

CREATE TABLE IF NOT EXISTS HOGWARTS.USER
(
    ID         BIGSERIAL PRIMARY KEY,
    GROUP_ID   BIGINT,
    FIRST_NAME TEXT NOT NULL,
    LAST_NAME  TEXT
);

CREATE TABLE IF NOT EXISTS HOGWARTS.USER_ROLE
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    UNIQUE (USER_ID, ROLE_ID)
);

CREATE TABLE IF NOT EXISTS HOGWARTS.LESSON
(
    ID                BIGSERIAL PRIMARY KEY,
    LESSON_DATE       DATE,
    START_END_TIME_ID INT,
    SUBJECT_ID        BIGINT,
    GROUP_ID          BIGINT,
    UNIQUE (LESSON_DATE, START_END_TIME_ID, GROUP_ID)
);

ALTER TABLE HOGWARTS.SUBJECT
    ADD CONSTRAINT PROFESSOR_ID_FK FOREIGN KEY (PROFESSOR_ID) REFERENCES HOGWARTS.USER (ID);
ALTER TABLE HOGWARTS.SUBJECT
    ADD CONSTRAINT CLASSROOM_ID_FK FOREIGN KEY (CLASSROOM_ID) REFERENCES HOGWARTS.CLASSROOM (ID);

ALTER TABLE HOGWARTS.USER
    ADD CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID);

ALTER TABLE HOGWARTS.USER_ROLE
    ADD CONSTRAINT USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES HOGWARTS.USER (ID);
ALTER TABLE HOGWARTS.USER_ROLE
    ADD CONSTRAINT ROLE_ID_FK FOREIGN KEY (ROLE_ID) REFERENCES HOGWARTS.ROLE (ID);

ALTER TABLE HOGWARTS.LESSON
    ADD CONSTRAINT START_END_TIME_ID_FK FOREIGN KEY (START_END_TIME_ID) REFERENCES HOGWARTS.LESSON_START_END_TIME (ID);
ALTER TABLE HOGWARTS.LESSON
    ADD CONSTRAINT SUBJECT_ID_FK FOREIGN KEY (SUBJECT_ID) REFERENCES HOGWARTS.SUBJECT (ID);
ALTER TABLE HOGWARTS.LESSON
    ADD CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID);

CREATE OR REPLACE FUNCTION error_if_not_a_professor_assigned_to_a_subject()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    IF EXISTS(SELECT 1
              FROM HOGWARTS.USER_ROLE
              WHERE USER_ID = NEW.professor_id
                AND ROLE_ID = (SELECT ID FROM HOGWARTS.ROLE WHERE NAME = 'student')) THEN
        RAISE unique_violation USING MESSAGE = 'Not a professor assigned to the subject!';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER check_if_user_is_a_professor
    BEFORE UPDATE
    ON HOGWARTS.SUBJECT
    FOR EACH ROW
EXECUTE PROCEDURE error_if_not_a_professor_assigned_to_a_subject();

CREATE OR REPLACE FUNCTION error_if_professor_or_admin_assigned_to_a_group()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    IF EXISTS(SELECT 1
              FROM HOGWARTS.USER_ROLE
              WHERE USER_ID =
                    (SELECT ID FROM HOGWARTS.USER where FIRST_NAME = OLD.first_name AND LAST_NAME = OLD.last_name)
                AND ROLE_ID = (SELECT ID FROM HOGWARTS.ROLE WHERE NAME = 'professor')) THEN
        RAISE unique_violation USING MESSAGE = 'Attempt to assign a professor to the group';
    END IF;

    IF EXISTS(SELECT 1
              FROM HOGWARTS.USER_ROLE
              WHERE USER_ID =
                    (SELECT ID FROM HOGWARTS.USER where FIRST_NAME = OLD.first_name AND LAST_NAME = OLD.last_name)
                AND ROLE_ID = (SELECT ID FROM HOGWARTS.ROLE WHERE NAME = 'admin')) THEN
        RAISE unique_violation USING MESSAGE = 'Attempt to assign an admin to the group';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER check_if_user_is_a_student
    BEFORE UPDATE OF GROUP_ID
    ON HOGWARTS.USER
    FOR EACH ROW
EXECUTE PROCEDURE error_if_professor_or_admin_assigned_to_a_group();