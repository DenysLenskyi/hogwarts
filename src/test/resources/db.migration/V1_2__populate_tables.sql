INSERT INTO HOGWARTS.ROLE (NAME) VALUES
('student'),
('professor'),
('admin');

INSERT INTO HOGWARTS.GROUP (NAME) VALUES ('Gryffindor');

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

INSERT INTO HOGWARTS.LESSON_START_END_TIME (ID, START_TIME, END_TIME) VALUES
(1, '09:30', '10:50'),
(2, '11:00', '12:20'),
(3, '13:00', '14:20'),
(4, '14:30', '15:50'),
(5, '16:00', '17:20');