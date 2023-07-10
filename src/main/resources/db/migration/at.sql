--ALTER TABLE HOGWARTS.SUBJECT
--    ADD CONSTRAINT PROFESSOR_ID_FK FOREIGN KEY (PROFESSOR_ID) REFERENCES HOGWARTS.USER (ID) ON DELETE CASCADE;
--ALTER TABLE HOGWARTS.SUBJECT
--    ADD CONSTRAINT CLASSROOM_ID_FK FOREIGN KEY (CLASSROOM_ID) REFERENCES HOGWARTS.CLASSROOM (ID) ON DELETE CASCADE;
--
--ALTER TABLE HOGWARTS.USER
--    ADD CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID) ON DELETE CASCADE;
--ALTER TABLE HOGWARTS.USER
--    ADD CONSTRAINT SUBJECT_ID_FK FOREIGN KEY (SUBJECT_ID) REFERENCES HOGWARTS.SUBJECT (ID) ON DELETE CASCADE;
--
--ALTER TABLE HOGWARTS.USER_ROLE
--    ADD CONSTRAINT USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES HOGWARTS.USER (ID) ON DELETE CASCADE;
--ALTER TABLE HOGWARTS.USER_ROLE
--    ADD CONSTRAINT ROLE_ID_FK FOREIGN KEY (ROLE_ID) REFERENCES HOGWARTS.ROLE (ID) ON DELETE CASCADE;
--
--ALTER TABLE HOGWARTS.LESSON
--    ADD CONSTRAINT START_END_TIME_ID_FK FOREIGN KEY (START_END_TIME_ID) REFERENCES HOGWARTS.LESSON_START_END_TIME (ID) ON DELETE CASCADE;
--ALTER TABLE HOGWARTS.LESSON
--    ADD CONSTRAINT SUBJECT_ID_FK FOREIGN KEY (SUBJECT_ID) REFERENCES HOGWARTS.SUBJECT (ID) ON DELETE CASCADE;
--ALTER TABLE HOGWARTS.LESSON
--    ADD CONSTRAINT GROUP_ID_FK FOREIGN KEY (GROUP_ID) REFERENCES HOGWARTS.GROUP (ID) ON DELETE CASCADE;