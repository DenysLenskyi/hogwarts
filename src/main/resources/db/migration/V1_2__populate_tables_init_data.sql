-- populate ROLE

INSERT INTO HOGWARTS.ROLE (NAME)
VALUES ('student'),
       ('professor'),
       ('admin');

-- populate GROUP

INSERT INTO HOGWARTS.GROUP (NAME)
VALUES ('Gryffindor-7');

-- populate USER

DO
$$
    DECLARE
        gryffindor_id BIGINT;
    BEGIN
        SELECT INTO gryffindor_id gr.ID FROM HOGWARTS.GROUP gr WHERE NAME = 'Gryffindor-7';

        INSERT INTO HOGWARTS.USER (GROUP_ID, FIRST_NAME, LAST_NAME)
        VALUES
            -- professors
            (null, 'Severus', 'Snape'),
            (null, 'Remus', 'Lupin'),
            (null, 'Pomona', 'Sprout'),
            (null, 'Minerva', 'McGonagall'),
            (null, 'Luna', 'Scamander'),
            (null, 'Firenze', ''),
            (null, 'Filius', 'Flitwick'),
            (null, 'Elspeth', 'MacGillony'),
            (null, 'Rolanda', 'Hooch'),
            (null, 'Jakub', 'Gorski'),
            (null, 'Septima', 'Vector'),
            (null, 'Rubeus', 'Hagrid'),
            -- students
            (gryffindor_id, 'Harry', 'Potter'),
            (gryffindor_id, 'Hermione', 'Granger'),
            (gryffindor_id, 'Neville', 'Longbottom'),
            (gryffindor_id, 'Ronald', 'Weasley'),
            (gryffindor_id, 'Lavender', 'Brown'),
            (gryffindor_id, 'Dean', 'Thomas'),
            (gryffindor_id, 'Seamus', 'Finnigan'),
            (gryffindor_id, 'Parvati', 'Patil');
    END;
$$;

-- populate USER_ROLE

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Severus' AND u.LAST_NAME = 'Snape' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Remus' AND u.LAST_NAME = 'Lupin' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Pomona' AND u.LAST_NAME = 'Sprout' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Minerva' AND u.LAST_NAME = 'McGonagall' and r.NAME = 'admin');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Minerva' AND u.LAST_NAME = 'McGonagall' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Luna' AND u.LAST_NAME = 'Scamander' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Firenze' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Filius' AND u.LAST_NAME = 'Flitwick' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Elspeth' AND u.LAST_NAME = 'MacGillony' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Rolanda' AND u.LAST_NAME = 'Hooch' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Jakub' AND u.LAST_NAME = 'Gorski' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Septima' AND u.LAST_NAME = 'Vector' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Rubeus' AND u.LAST_NAME = 'Hagrid' and r.NAME = 'professor');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Harry' AND u.LAST_NAME = 'Potter' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Hermione' AND u.LAST_NAME = 'Granger' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Neville' AND u.LAST_NAME = 'Longbottom' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Ronald' AND u.LAST_NAME = 'Weasley' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Lavender' AND u.LAST_NAME = 'Brown' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Dean' AND u.LAST_NAME = 'Thomas' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Seamus' AND u.LAST_NAME = 'Finnigan' and r.NAME = 'student');

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT u.ID, r.ID
FROM HOGWARTS.USER u
         JOIN HOGWARTS.ROLE r ON (u.FIRST_NAME = 'Parvati' AND u.LAST_NAME = 'Patil' and r.NAME = 'student');

-- populate CLASSROOM

INSERT INTO HOGWARTS.CLASSROOM (NAME, DESCRIPTION)
VALUES ('Class 1',
        'Class 1 was a classroom located on the first floor of Hogwarts Castle. First-year Transfiguration classes were taught there in the 1991–1992 school year.'),
       ('Dungeon Five',
        'Dungeon Five was a classroom in Hogwarts Castle used for Potions classes. In October 1992, a number of third years accidentally covered the dungeon''s ceiling in frog brains, which Argus Filch had to clean.'),
       ('Potion-Mixing Room',
        'The Potion-Mixing Room was a potions laboratory located in the dungeons of Hogwarts Castle. Its use was reserved for advanced Potions students.'),
       ('Potions Classroom',
        'The Potions Classroom was one of the large dungeons in Hogwarts Castle, used for Potions lessons.'),
       ('Hagrid''s Hut',
        'Hagrid''s Hut served as a home to Rubeus Hagrid (and some of his pets, including Fang) during his years as Gamekeeper and teaching at Hogwarts School of Witchcraft and Wizardry.'),
       ('Forbidden Forest',
        'The Forbidden Forest, also known as the Dark Forest, was a forest that bordered the edges of the grounds of Hogwarts School of Witchcraft and Wizardry.'),
       ('Class 31',
        'Class 31 was a classroom located on the second floor of Hogwarts Castle. First-year Defence Against the Dark Arts classes were taught there in the 1991–1992 school year.'),
       ('Classroom 3C',
        'Classroom 3C, also known as the Defence Against the Dark Arts Classroom, was where Defence Against the Dark Arts class was taught at Hogwarts School of Witchcraft and Wizardry. It was located off of the Serpentine Corridor on the third-floor of Hogwarts Castle.'),
       ('Greenhouse 1',
        'Greenhouse One was where the first year Herbology classes were taught at Hogwarts School of Witchcraft and Wizardry. The room had no dangerous species as it was for the first-year students.'),
       ('Greenhouse 2',
        'Greenhouse Two was a greenhouse in which Herbology classes were taught at Hogwarts School of Witchcraft and Wizardry.'),
       ('Greenhouse 3',
        'Greenhouse Three was where Herbology classes were taught to every student, except first-years, because it was used to house several spices of magical plants that were deemed too dangerous for them to handle. The room had more dangerous species, such as the Venomous Tentacula, as it was for the advanced students.'),
       ('Greenhouse 4',
        'Greenhouse Four was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was presumably used as a Herbology classroom for advanced students.'),
       ('Greenhouse 5',
        'Greenhouse Five was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was presumably used as a Herbology classroom for advanced students, that decided to take the subject to N.E.W.T.-level.'),
       ('Greenhouse 6',
        'Greenhouse Six was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was used as a storage space, and it was unknown whether advanced-level Herbology classes were taught here. Inside, there was a small mushroom patch, hedges, and little flowers rose from the greenhouse floor. This greenhouse was an out-of-bounds area for first year students, and was usually patrolled by Prefects.'),
       ('Greenhouse 7',
        'Greenhouse Seven was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was presumed to be used as a Herbology classroom for more advanced students.'),
       ('Class 102',
        'Class 102 was a classroom located in the South Tower at Hogwarts Castle. First-year (presumably theoretical) Herbology classes were taught there in the 1991–1992 school year.'),
       ('Classroom 1B',
        'Classroom 1B, also known as the Transfiguration Classroom, was a classroom on the ground-floor of Hogwarts Castle, accessible from the Middle Courtyard.'),
       ('Class 34',
        'Class 34 was a classroom located on the third floor of Hogwarts Castle. First-year Transfiguration classes were taught there in the 1991–1992 school year.'),
       ('Backyard',
        'Backyard is a yard located in the middle of the Hogwarts Castle it served as a place to take flying practice for all the students'),
       ('Divination Classroom',
        'The Divination Classroom was where Divination classes were taught at Hogwarts School of Witchcraft and Wizardry. It was located in the North Tower. It was accessible through a circular trapdoor and was described as looking like a cross between somebody''s attic and an old-fashioned teashop.'),
       ('Classroom 2E',
        'The Charms Classroom (designated Classroom 2E) was where Charms classes were taught at Hogwarts School of Witchcraft and Wizardry, and it was located on the third floor, on the Charms corridor, near another classroom.'),
       ('Classroom 4F',
        'The History of Magic Classroom (designated Classroom 4F) was where History of Magic classes were taught at Hogwarts School of Witchcraft and Wizardry. It was located on the first floor of Hogwarts Castle.'),
       ('Classroom 7A',
        'Classroom 7A was a classroom where Arithmancy classes were taught at Hogwarts School of Witchcraft and Wizardry. It was located off of the Serpentine Corridor on the third-floor of Hogwarts Castle, next-door to Classroom 3C and around the corner from a corridor where the staffroom sometimes existed.'),
       ('Classroom 6A',
        'Classroom 6A was where Study of Ancient Runes and Ancient Studies classes were taught at Hogwarts. It was located on the sixth floor of Hogwarts Castle, next-door to Classroom 6B, and included some desks, a bookcase, and a lectern for the teacher.');

-- populate LESSON_START_END_TIME

INSERT INTO HOGWARTS.LESSON_START_END_TIME (ID, START_TIME, END_TIME)
VALUES (nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '09:30', '10:50'),
       (nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '11:00', '12:20'),
       (nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '13:00', '14:20'),
       (nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '14:30', '15:50'),
       (nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '16:00', '17:20');

-- populate SUBJECT

DO
$$
    DECLARE
        severus_snape_id BIGINT;
        potions_classroom_id BIGINT;

        rubeus_hagrid_id BIGINT;
        hagrids_hut_id BIGINT;

        remus_lupin_id BIGINT;
        classroom_3c_id BIGINT;

        pomona_sprout_id BIGINT;
        class_102_id BIGINT;

        minerva_mcgonagall_id BIGINT;
        class_34_id BIGINT;

        luna_scamander_id BIGINT;
        forbidden_forest_id BIGINT;

        firenze_id BIGINT;
        divination_classroom_id BIGINT;

        filius_flitwick_id BIGINT;
        classroom_2e_id BIGINT;

        elspeth_macgillony_id BIGINT;
        classroom_6a_id BIGINT;

        rolanda_hooch_id BIGINT;
        backyard_id BIGINT;

        jakub_gorski_id BIGINT;
        classroom_4f_id BIGINT;

        septima_vector_id BIGINT;
        classroom_7a_id BIGINT;

    BEGIN
        SELECT INTO severus_snape_id u.ID FROM HOGWARTS.USER u WHERE first_name = 'Severus' AND last_name = 'Snape';
        SELECT INTO potions_classroom_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Potions Classroom';

        SELECT INTO rubeus_hagrid_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Rubeus' AND u.LAST_NAME = 'Hagrid';
        SELECT INTO hagrids_hut_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name like 'Hagrid%% Hut';

        SELECT INTO remus_lupin_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Remus' AND u.LAST_NAME = 'Lupin';
        SELECT INTO classroom_3c_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Classroom 3C';

        SELECT INTO pomona_sprout_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Pomona' AND u.LAST_NAME = 'Sprout';
        SELECT INTO class_102_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Class 102';

        SELECT INTO minerva_mcgonagall_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Minerva' AND u.LAST_NAME = 'McGonagall';
        SELECT INTO class_34_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Class 34';

        SELECT INTO luna_scamander_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Luna' AND u.LAST_NAME = 'Scamander';
        SELECT INTO forbidden_forest_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Forbidden Forest';

        SELECT INTO firenze_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Firenze';
        SELECT INTO divination_classroom_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Divination Classroom';

        SELECT INTO filius_flitwick_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Filius' AND u.LAST_NAME = 'Flitwick';
        SELECT INTO classroom_2e_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Classroom 2E';

        SELECT INTO elspeth_macgillony_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Elspeth' AND u.LAST_NAME = 'MacGillony';
        SELECT INTO classroom_6a_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Classroom 6A';

        SELECT INTO rolanda_hooch_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Rolanda' AND u.LAST_NAME = 'Hooch';
        SELECT INTO backyard_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Backyard';

        SELECT INTO jakub_gorski_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Jakub' AND u.LAST_NAME = 'Gorski';
        SELECT INTO classroom_4f_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Classroom 4F';

        SELECT INTO septima_vector_id u.ID FROM HOGWARTS.USER u WHERE u.FIRST_NAME = 'Septima' AND u.LAST_NAME = 'Vector';
        SELECT INTO classroom_7a_id room.ID FROM HOGWARTS.CLASSROOM room WHERE name = 'Classroom 7A';

        INSERT INTO HOGWARTS.SUBJECT (NAME, DESCRIPTION, PROFESSOR_ID, CLASSROOM_ID) VALUES
            ('Potions',
             'In this class, students learnt the correct way to brew potions. They followed specific recipes and used various magical ingredients to create potions, starting with simple ones and moving to more advanced ones as they progressed in knowledge.',
             severus_snape_id, potions_classroom_id),
            ('Care of Magical Creatures',
             'In the class, students learnt about a wide range of magical creatures, from flobberworms, hippogriffs, unicorns and even thestrals. Students were taught about feeding, maintaining, breeding, and proper treatment of these creatures and many more.',
             rubeus_hagrid_id, hagrids_hut_id),
            ('Defence Against the Dark Arts',
             'In this class, students studied and learnt how to defend themselves against all aspects of the Dark Arts, including dark creatures, curses, hexes and jinxes (dark charms), and duelling.',
             remus_lupin_id, classroom_3c_id),
            ('Herbology',
             'In this class students learned to care for and utilise plants, learn about their magical properties and what they are used for. Many plants provided ingredients for potions and medicine, while others had magical effects of their own right.',
             pomona_sprout_id, class_102_id),
            ('Transfiguration',
             'Transfiguration was a core class and subject taught at Hogwarts School of Witchcraft and Wizardry, Ilvermorny School of Witchcraft and Wizardry and Uagadou. It taught the art of changing the form and appearance of an object or a person. This type of magic was commonly referred to as "Transfiguration" and was considered both complex and dangerous.',
             minerva_mcgonagall_id, class_34_id),
            ('Field Studies',
             'Field Studies class was proposed by the British Ministry of Magic official Archibald Eagleton in the 2010s. It involved studying magical creatures in nature.',
             luna_scamander_id, forbidden_forest_id),
            ('Divination',
             'Divination was an elective course taught at Hogwarts School of Witchcraft and Wizardry. It taught methods of divining the future, or gathering insights into future events, through various rituals and tools.',
             firenze_id, divination_classroom_id),
            ('Charms',
             'Charms was a core class and subject taught at Hogwarts School of Witchcraft and Wizardry and Ilvermorny School of Witchcraft and Wizardry. As the name suggests, it specialised in the teaching of charms.',
             filius_flitwick_id, classroom_2e_id),
            ('Study of Ancient Runes',
             'Ancient runes were a form of writing which witches and wizards used hundreds of years ago.',
             elspeth_macgillony_id, classroom_6a_id),
            ('Flying',
             'Flying, also known as Broom Flight Class, was a subject taught at Hogwarts School of Witchcraft and Wizardry. It was taught by Madam Rolanda Hooch, the Hogwarts Flying Instructor and Quidditch referee. The subject taught students how to fly broomsticks.',
             rolanda_hooch_id, backyard_id),
            ('History of Magic',
             'This class was a study of magical history. This was one of the subjects where the use of magic practically was not necessary. History of Magic was taught from the first year to the fifth, and was completed with an O.W.L. exam with only a written section.',
             jakub_gorski_id, classroom_4f_id),
            ('Arithmancy',
             'Little is known about the class, but the study of Arithmancy has been described as "predicting the future using numbers", with "bit of numerology" as well.',
             septima_vector_id, classroom_7a_id);
    END
$$;