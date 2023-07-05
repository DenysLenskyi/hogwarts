INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Severus' AND u.LAST_NAME = 'Snape'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Remus' AND u.LAST_NAME = 'Lupin'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Pomona' AND u.LAST_NAME = 'Sprout'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Minerva' AND u.LAST_NAME = 'McGonagall'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Minerva' AND u.LAST_NAME = 'McGonagall'
                  and r.NAME = 'admin'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Luna' AND u.LAST_NAME = 'Scamander'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Firenze'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Filius' AND u.LAST_NAME = 'Flitwick'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Elspeth' AND u.LAST_NAME = 'MacGillony'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Rolanda' AND u.LAST_NAME = 'Hooch'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Jakub' AND u.LAST_NAME = 'Gorski'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Septima' AND u.LAST_NAME = 'Vector'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Rubeus' AND u.LAST_NAME = 'Hagrid'
                  and r.NAME = 'professor'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Harry' AND u.LAST_NAME = 'Potter'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Hermione' AND u.LAST_NAME = 'Granger'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Neville' AND u.LAST_NAME = 'Longbottom'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Ronald' AND u.LAST_NAME = 'Weasley'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Lavender' AND u.LAST_NAME = 'Brown'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Dean' AND u.LAST_NAME = 'Thomas'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Seamus' AND u.LAST_NAME = 'Finnigan'
                  and r.NAME = 'student'
                  );
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r
              ON (u.FIRST_NAME = 'Parvati' AND u.LAST_NAME = 'Patil'
                  and r.NAME = 'student'
                  );