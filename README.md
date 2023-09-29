# HOGWARTS SCHEDULE MANAGER APP

Thank you for your attention to my web application. Next, I will tell you what it is and how to use it.

## TABLE OF CONTENTS

+ Summary
+ Application structure
  1. Login
  2. Users
  3. Permissions
  4. adjustments
+ How to use

## SUMMARY

### Personal

HOGWARTS SCHEDULE MANAGER is my educational project, so it shouldn't be treated as an enterprise application. It's more like an alpha version that may have some issues. My goal was to use and implement certain backend technologies, while all other development aspects either remain at a very basic level or are completely absent.

The development process itself was interesting for me; I got acquainted with technologies like Spring MVC, Security, Thymeleaf, CI/CD, and AWS. I would say I'm proud of the result.

### About the APP

The main function of the application is the ability to create schedules for Hogwarts School of Witchcraft and Wizardry. The schedule includes date, time, student group, teacher (professor), subject, and classroom. The administrator has the ability to create, delete, and edit all these components. Some sample data is provided by default for demonstration purposes.

Only the names and titles are taken from the Harry Potter lore. We do not claim any adherence to the books or movies or to any canonical lore.

## APPLICATION STRUCTURE

### Login

To use the APP you have to login first. You may login as:
+ student
+ professor
+ admin

By default Minerva McGonagall has professor and admin roles. Any professor could have the admin role. Student can't.

### Default users

**IMPORTANT!** This application is open to the world, so anyone can log in with administrator privileges and make changes to users list. If you can't log in, please contact the developer at denislensky94@gmail.com.

List of Users (first name, last name, username (password is the same as username), authority):

+ Severus Snape, severussnape, professor,
+ Rubeus Hagrid, rubeushagrid, professor,
+ Remus Lupin, remuslupin, professor,
+ Pomona Sprout, pomonasprout, professor,
+ Minerva McGonagall, minervamcgonagall, professor, admin,
+ Luna Scamander, lunascamander, professor,
+ Firenze, firenze, professor,
+ Filius Flitwick, filiusflitwick, professor,
+ Elspeth MacGillony, elspethmacgillony, professor,
+ Rolanda Hooch, rolandahooch, professor,
+ Jakub Gorski, jakubgorski, professor,
+ Septima Vector, septimavector, professor,
+ Harry Potter, harrypotter, student,
+ Hermione Granger, hermionegranger, student,
+ Neville Longbottom, nevillelongbottom, student,
+ Ronald Weasley, ronaldweasley, student,
+ Lavender Brown, lavenderbrown, student,
+ Dean Thomas, deanthomas, student,
+ Seamus Finnigan, seamusfinnigan, student,
+ Parvati Patil, parvatipatil, student.

### Permissions

Student can only read.
Professor can read and edit the classroom for a subject.
Admin can perform any CRUD operation with classrooms, subjects, users, groups, schedule.

### Default adjustments
+ A subject has only one classroom
+ Could be subjects with no classroom and vice versa
+ Possible role combinations for users:
  + student
  + professor
  + admin
  + professor, admin
+ Professors have only one subject
+ Could be a professor with no subject and vice versa

## HOW TO USE THE APP

### Remote server

Copy this address to your browser - 3.16.216.231:8080 (no http://, no www, just this numbers). You should get the log in page.

### Localhost
+ Download source code
+ Download and install Docker
+ Run the command:
  + docker run --name=hogwarts_db -d -e POSTGRES_DB=hogwarts_db -e POSTGRES_USER=lenskyi -e POSTGRES_PASSWORD=lenskyi -p 5432:5432 postgres:15-alpine
+ Free 8080 port
+ Execute code from IDE
+ Another option is to build .jar and docker image, and execute docker-compose file