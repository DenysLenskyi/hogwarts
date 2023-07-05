How to use APP:
1. Download source code
2. Build jar
3. Build Docker image
4. Execute docker-compose file
5. Go to webpage
6. Sign in as student, professor or admin
7. View schedule

Run DB with docker:

docker run --name=spring-mvc-docker-hogwarts -d -e POSTGRES_DB=hogwarts_db -e POSTGRES_USER=lenskyi -e POSTGRES_PASSWORD=lenskyi -p 5432:5432 postgres:15-alpine

Hogwarts Schedule Manager

One teacher one subject

One subject one classroom

Classes default daily schedule (max 5 classes a day):

1. 09:30 - 10:50
2. 11:00 - 12:20
3. 13:00 - 14:20
4. 14:30 - 15:50
5. 16:00 - 17:20

Houses (faculties): Gryffindor, Hufflepuff, Ravenclaw, Slytherin
The sample of a students group name: «Gryffindor-7-1»; «houseName-yearOfStudy-groupId». They study 7 years at Hogwarts.
User has one of 3 available roles: student, professor, admin

Admin options:
Edit database (CRUD student, teacher, course and relations between them)
User options:
Navigate to MySchedule
Choose a time window for schedule viewing (default «current month»)

List of used subjects:

Potions,
Care of Magical Creatures,
Defence Against the Dark Arts,
Herbology,
Transfiguration,
Field Studies,
Divination,
Charms,
Study of Ancient Runes,
Flying,
History of Magic,
Arithmancy.

List of professors:

Severus Snape,
Rubeus Hagrid,
Remus Lupin,
Pomona Sprout,
Minerva McGonagall,
Luna Scamander,
Firenze,
Filius Flitwick,
Elspeth MacGillony,
Rolanda Hooch,
Jakub Gorski,
Septima Vector.

List of classrooms:

Dungeon Five,
Potion-Mixing Room,
Potions Classroom,
Hagrid's Hut,
Forbidden Forest,
Class 31,
Classroom 3C,
Greenhouse 1-7,
Class 102,
Classroom 1B,
Class 1,
Class 34,
Backyard,
Classroom 1,
Divination Classroom,
Classroom 2E,
Classroom 6A,
Backyard,
Classroom 4F,
Classroom 7A.