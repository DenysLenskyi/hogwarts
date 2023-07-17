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

CREATE SEQUENCE HOGWARTS.LESSON_START_END_TIME_ID_SEQ
    START 1
    INCREMENT 1;

CREATE TABLE IF NOT EXISTS HOGWARTS.LESSON_START_END_TIME
(
    ID         BIGINT NOT NULL UNIQUE DEFAULT nextval('LESSON_START_END_TIME_ID_SEQ'),
    START_TIME TIME UNIQUE NOT NULL ,
    END_TIME   TIME UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS HOGWARTS.CLASSROOM
(
    ID          BIGSERIAL PRIMARY KEY,
    NAME        TEXT UNIQUE NOT NULL,
    DESCRIPTION TEXT
);

CREATE TABLE IF NOT EXISTS HOGWARTS.USER
(
    ID         BIGSERIAL PRIMARY KEY,
    GROUP_ID   BIGINT,
    FIRST_NAME TEXT NOT NULL,
    LAST_NAME  TEXT,
    CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID)
);

CREATE TABLE IF NOT EXISTS HOGWARTS.SUBJECT
(
    ID           BIGSERIAL PRIMARY KEY,
    NAME         TEXT UNIQUE NOT NULL,
    DESCRIPTION  TEXT,
    PROFESSOR_ID BIGINT,
    CLASSROOM_ID BIGINT,
    CONSTRAINT CLASSROOM_ID_FK FOREIGN KEY (CLASSROOM_ID) REFERENCES HOGWARTS.CLASSROOM (ID),
    CONSTRAINT PROFESSOR_ID_FK FOREIGN KEY (PROFESSOR_ID) REFERENCES HOGWARTS.USER (ID)
);

CREATE TABLE IF NOT EXISTS HOGWARTS.USER_ROLE
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    UNIQUE (USER_ID, ROLE_ID),
    CONSTRAINT USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES HOGWARTS.USER (ID),
    CONSTRAINT ROLE_ID_FK FOREIGN KEY (ROLE_ID) REFERENCES HOGWARTS.ROLE (ID)
);

CREATE TABLE IF NOT EXISTS HOGWARTS.LESSON
(
    ID                BIGSERIAL PRIMARY KEY,
    LESSON_DATE       DATE,
    START_END_TIME_ID BIGINT,
    SUBJECT_ID        BIGINT,
    GROUP_ID          BIGINT,
    UNIQUE (LESSON_DATE, START_END_TIME_ID, GROUP_ID),
    CONSTRAINT START_END_TIME_ID_FK FOREIGN KEY (START_END_TIME_ID) REFERENCES HOGWARTS.LESSON_START_END_TIME (ID),
    CONSTRAINT SUBJECT_ID_FK FOREIGN KEY (SUBJECT_ID) REFERENCES HOGWARTS.SUBJECT (ID),
    CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID)
);

CREATE FUNCTION check_professor_role_id()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
DECLARE
    professor_role_id BIGINT;
    admin_role_id     BIGINT;
    current_role_id   BIGINT;
BEGIN
    select INTO professor_role_id id
    from HOGWARTS.ROLE
    where name LIKE 'professor';

    select INTO admin_role_id id
    from HOGWARTS.ROLE
    where name LIKE 'admin';

    select into current_role_id role_id
    from HOGWARTS.USER_ROLE
    where user_id = NEW.PROFESSOR_ID;

    IF professor_role_id <> current_role_id and admin_role_id <> current_role_id
    THEN
        RAISE EXCEPTION 'in table HOGWARTS.SUBJECT column PROFESSOR_ID can save only users with role `professor`';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER professor_role_id_trigger
    BEFORE INSERT OR UPDATE
    ON HOGWARTS.SUBJECT
    FOR EACH ROW
EXECUTE FUNCTION check_professor_role_id();

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

CREATE TRIGGER check_if_user_is_a_student_when_update_user_group_id
    BEFORE UPDATE OF GROUP_ID
    ON HOGWARTS.USER
    FOR EACH ROW
EXECUTE PROCEDURE error_if_professor_or_admin_assigned_to_a_group();

CREATE FUNCTION validate_group()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
DECLARE
    new_user_role     TEXT;
    new_user_group_id BIGINT;
BEGIN
    select INTO new_user_role R.NAME
    from HOGWARTS.ROLE R
    where R.ID = NEW.ROLE_ID;

    select into new_user_group_id GROUP_ID
    from HOGWARTS.USER
    where id = NEW.USER_ID;

    IF new_user_role = 'student' AND new_user_group_id IS NULL
    THEN
        RAISE EXCEPTION 'in table HOGWARTS.USER column GROUP_ID must have value for users with role `student`';
    ELSIF new_user_role = 'admin' AND new_user_group_id is not null OR
          new_user_role = 'professor' AND new_user_group_id is not null
    THEN
        RAISE EXCEPTION 'in table HOGWARTS.USER column GROUP_ID should not have value for users with role `admin` or `professor`';
    ELSE
        RETURN NEW;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER user_group_trigger
    BEFORE INSERT OR UPDATE
    ON HOGWARTS.USER_ROLE
    FOR EACH ROW
EXECUTE FUNCTION validate_group();