package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "SUBJECT", schema = "HOGWARTS")
public class Subject {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PROFESSOR_ID", insertable = false, updatable = false)
    private Long professorId;

    @Column(name = "CLASSROOM_ID", insertable = false, updatable = false)
    private Long classroomId;

    @Column(name = "NAME")
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(mappedBy = "professorSubject")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLASSROOM_ID", referencedColumnName = "ID")
    private Classroom subjectClassroom;

    @OneToMany(mappedBy = "subjectOfTheLesson")
    private Set<Lesson> lessons;

    public Subject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classroom getSubjectClassroom() {
        return subjectClassroom;
    }

    public void setSubjectClassroom(Classroom subjectClassroom) {
        this.subjectClassroom = subjectClassroom;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
