ALTER TABLE hogwarts.lesson
    DROP CONSTRAINT subject_id_fk;
ALTER TABLE hogwarts.lesson
    ADD CONSTRAINT SUBJECT_ID_FK FOREIGN KEY (SUBJECT_ID) REFERENCES HOGWARTS.SUBJECT (ID) ON DELETE CASCADE;

ALTER TABLE hogwarts.lesson
    DROP CONSTRAINT group_id_fk;
ALTER TABLE hogwarts.lesson
    ADD CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID) ON DELETE CASCADE;

ALTER TABLE hogwarts.subject
    DROP CONSTRAINT classroom_id_fk;
ALTER TABLE hogwarts.subject
    ADD CONSTRAINT classroom_id_fk FOREIGN KEY (classroom_id) REFERENCES hogwarts.classroom (ID) ON DELETE SET NULL;

ALTER TABLE hogwarts.subject
    DROP CONSTRAINT professor_id_fk;
ALTER TABLE hogwarts.subject
    ADD CONSTRAINT professor_id_fk FOREIGN KEY (professor_id) REFERENCES hogwarts.user (ID) ON DELETE SET NULL;