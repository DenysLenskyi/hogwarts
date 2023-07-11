INSERT INTO HOGWARTS.ROLE (NAME) VALUES
('student'),
('professor'),
('admin');

INSERT INTO HOGWARTS.GROUP (NAME) VALUES ('Gryffindor-7');

INSERT INTO HOGWARTS.LESSON_START_END_TIME (ID, START_TIME, END_TIME) VALUES
(nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '09:30', '10:50'),
(nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '11:00', '12:20'),
(nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '13:00', '14:20'),
(nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '14:30', '15:50'),
(nextval('HOGWARTS.LESSON_START_END_TIME_ID_SEQ'), '16:00', '17:20');

do $$
declare
    gryffindor_id BIGINT;
begin
    SELECT INTO gryffindor_id gr.ID FROM HOGWARTS.GROUP gr WHERE NAME = 'Gryffindor-7';

    INSERT INTO HOGWARTS.USER (GROUP_ID, FIRST_NAME, LAST_NAME) VALUES
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
    (gryffindor_id, 'Harry', 'Potter'),
    (gryffindor_id, 'Hermione', 'Granger'),
    (gryffindor_id, 'Neville', 'Longbottom'),
    (gryffindor_id, 'Ronald', 'Weasley'),
    (gryffindor_id, 'Lavender', 'Brown'),
    (gryffindor_id, 'Dean', 'Thomas'),
    (gryffindor_id, 'Dean', 'Finnigan'),
    (gryffindor_id, 'Seamus', 'Patil' );
end; $$;

-- the same for user_role

INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT U.ID, R.ID FROM HOGWARTS.USER U JOIN HOGWARTS.ROLE R ON (u.FIRST_NAME = 'Severus' AND u.LAST_NAME = 'Snape' and r.NAME = 'professor');
--SELECT U.ID, R.ID FROM HOGWARTS.USER U JOIN HOGWARTS.ROLE R ON (u.FIRST_NAME = 'Remus' AND u.LAST_NAME = 'Lupin' and r.NAME = 'professor'),
--SELECT U.ID, R.ID FROM HOGWARTS.USER U JOIN HOGWARTS.ROLE R ON (u.FIRST_NAME = 'Pomona' AND u.LAST_NAME = 'Sprout' and r.NAME = 'professor'),
--SELECT U.ID, R.ID FROM HOGWARTS.USER U JOIN HOGWARTS.ROLE R ON (u.FIRST_NAME = 'Minerva' AND u.LAST_NAME = 'McGonagall' and r.NAME = 'professor'),
INSERT INTO HOGWARTS.USER_ROLE (USER_ID, ROLE_ID)
SELECT U.ID, R.ID FROM HOGWARTS.USER U JOIN HOGWARTS.ROLE R ON (u.FIRST_NAME = 'Harry' AND u.LAST_NAME = 'Potter' and r.NAME = 'student');
-- next line can trigger exception in validate_group function
--SELECT U.ID, R.ID FROM HOGWARTS.USER U JOIN HOGWARTS.ROLE R ON (u.FIRST_NAME = 'Harry' AND u.LAST_NAME = 'Potter' and r.NAME = 'professor');
-- continue

INSERT INTO HOGWARTS.CLASSROOM (NAME, DESCRIPTION) VALUES
('Class 1', 'Class 1 was a classroom located on the first floor of Hogwarts Castle. First-year Transfiguration classes were taught there in the 1991–1992 school year.'),
('Dungeon Five', 'Dungeon Five was a classroom in Hogwarts Castle used for Potions classes. In October 1992, a number of third years accidentally covered the dungeon''s ceiling in frog brains, which Argus Filch had to clean.'),
('Potion-Mixing Room', 'The Potion-Mixing Room was a potions laboratory located in the dungeons of Hogwarts Castle. Its use was reserved for advanced Potions students.'),
('Potions Classroom', 'The Potions Classroom was one of the large dungeons in Hogwarts Castle, used for Potions lessons.'),
('Hagrid''s Hut', 'Hagrid''s Hut served as a home to Rubeus Hagrid (and some of his pets, including Fang) during his years as Gamekeeper and teaching at Hogwarts School of Witchcraft and Wizardry.'),
('Forbidden Forest', 'The Forbidden Forest, also known as the Dark Forest, was a forest that bordered the edges of the grounds of Hogwarts School of Witchcraft and Wizardry.'),
('Class 31', 'Class 31 was a classroom located on the second floor of Hogwarts Castle. First-year Defence Against the Dark Arts classes were taught there in the 1991–1992 school year.'),
('Classroom 3C', 'Classroom 3C, also known as the Defence Against the Dark Arts Classroom, was where Defence Against the Dark Arts class was taught at Hogwarts School of Witchcraft and Wizardry. It was located off of the Serpentine Corridor on the third-floor of Hogwarts Castle.'),
('Greenhouse 1', 'Greenhouse One was where the first year Herbology classes were taught at Hogwarts School of Witchcraft and Wizardry. The room had no dangerous species as it was for the first-year students.'),
('Greenhouse 2', 'Greenhouse Two was a greenhouse in which Herbology classes were taught at Hogwarts School of Witchcraft and Wizardry.'),
('Greenhouse 3', 'Greenhouse Three was where Herbology classes were taught to every student, except first-years, because it was used to house several spices of magical plants that were deemed too dangerous for them to handle. The room had more dangerous species, such as the Venomous Tentacula, as it was for the advanced students.'),
('Greenhouse 4', 'Greenhouse Four was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was presumably used as a Herbology classroom for advanced students.'),
('Greenhouse 5', 'Greenhouse Five was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was presumably used as a Herbology classroom for advanced students, that decided to take the subject to N.E.W.T.-level.'),
('Greenhouse 6', 'Greenhouse Six was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was used as a storage space, and it was unknown whether advanced-level Herbology classes were taught here. Inside, there was a small mushroom patch, hedges, and little flowers rose from the greenhouse floor. This greenhouse was an out-of-bounds area for first year students, and was usually patrolled by Prefects.'),
('Greenhouse 7', 'Greenhouse Seven was a greenhouse at Hogwarts School of Witchcraft and Wizardry. It was presumed to be used as a Herbology classroom for more advanced students.'),
('Class 102', 'Class 102 was a classroom located in the South Tower at Hogwarts Castle. First-year (presumably theoretical) Herbology classes were taught there in the 1991–1992 school year.'),
('Classroom 1B', 'Classroom 1B, also known as the Transfiguration Classroom, was a classroom on the ground-floor of Hogwarts Castle, accessible from the Middle Courtyard.'),
('Class 34', 'Class 34 was a classroom located on the third floor of Hogwarts Castle. First-year Transfiguration classes were taught there in the 1991–1992 school year.'),
('Backyard', 'Backyard is a yard located in the middle of the Hogwarts Castle it served as a place to take flying practice for all the students'),
('Divination Classroom', 'The Divination Classroom was where Divination classes were taught at Hogwarts School of Witchcraft and Wizardry. It was located in the North Tower. It was accessible through a circular trapdoor and was described as looking like a cross between somebody''s attic and an old-fashioned teashop.'),
('Classroom 2E', 'The Charms Classroom (designated Classroom 2E) was where Charms classes were taught at Hogwarts School of Witchcraft and Wizardry, and it was located on the third floor, on the Charms corridor, near another classroom.'),
('Classroom 4F', 'The History of Magic Classroom (designated Classroom 4F) was where History of Magic classes were taught at Hogwarts School of Witchcraft and Wizardry. It was located on the first floor of Hogwarts Castle.'),
('Classroom 7A', 'Classroom 7A was a classroom where Arithmancy classes were taught at Hogwarts School of Witchcraft and Wizardry. It was located off of the Serpentine Corridor on the third-floor of Hogwarts Castle, next-door to Classroom 3C and around the corner from a corridor where the staffroom sometimes existed.'),
('Classroom 6A', 'Classroom 6A was where Study of Ancient Runes and Ancient Studies classes were taught at Hogwarts. It was located on the sixth floor of Hogwarts Castle, next-door to Classroom 6B, and included some desks, a bookcase, and a lectern for the teacher.');

--todo
INSERT INTO HOGWARTS.SUBJECT (NAME, DESCRIPTION, PROFESSOR_ID, CLASSROOM_ID) VALUES
('Potions', 'In this class, students learnt the correct way to brew potions. They followed specific recipes and used various magical ingredients to create potions, starting with simple ones and moving to more advanced ones as they progressed in knowledge.', null, null),
('Defence Against the Dark Arts', 'In this class, students studied and learnt how to defend themselves against all aspects of the Dark Arts, including dark creatures, curses, hexes and jinxes (dark charms), and duelling.', null, null),
('Herbology', 'In this class students learned to care for and utilise plants, learn about their magical properties and what they are used for. Many plants provided ingredients for potions and medicine, while others had magical effects of their own right.', null, null),
('Transfiguration', 'Transfiguration was a core class and subject taught at Hogwarts School of Witchcraft and Wizardry, Ilvermorny School of Witchcraft and Wizardry and Uagadou. It taught the art of changing the form and appearance of an object or a person. This type of magic was commonly referred to as "Transfiguration" and was considered both complex and dangerous.', null, null),
('Field Studies', 'Field Studies class was proposed by the British Ministry of Magic official Archibald Eagleton in the 2010s. It involved studying magical creatures in nature.', null, null),
('Divination', 'Divination was an elective course taught at Hogwarts School of Witchcraft and Wizardry. It taught methods of divining the future, or gathering insights into future events, through various rituals and tools.', null, null),
('Charms', 'Charms was a core class and subject taught at Hogwarts School of Witchcraft and Wizardry and Ilvermorny School of Witchcraft and Wizardry. As the name suggests, it specialised in the teaching of charms.', null, null),
('Study of Ancient Runes', 'Ancient runes were a form of writing which witches and wizards used hundreds of years ago.', null, null),
('Flying', 'Flying, also known as Broom Flight Class, was a subject taught at Hogwarts School of Witchcraft and Wizardry. It was taught by Madam Rolanda Hooch, the Hogwarts Flying Instructor and Quidditch referee. The subject taught students how to fly broomsticks.', null, null),
('History of Magic', 'This class was a study of magical history. This was one of the subjects where the use of magic practically was not necessary. History of Magic was taught from the first year to the fifth, and was completed with an O.W.L. exam with only a written section.', null, null),
('Arithmancy', 'Little is known about the class, but the study of Arithmancy has been described as "predicting the future using numbers", with "bit of numerology" as well.', null, null),
('Care of Magical Creatures', 'In the class, students learnt about a wide range of magical creatures, from flobberworms, hippogriffs, unicorns and even thestrals. Students were taught about feeding, maintaining, breeding, and proper treatment of these creatures and many more.', null, null);
