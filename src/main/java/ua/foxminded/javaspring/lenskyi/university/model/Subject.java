package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "SUBJECT", schema = "HOGWARTS")
public class Subject {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne
    @JoinColumn(name = "PROFESSOR_ID", referencedColumnName = "ID")
    private User professor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLASSROOM_ID", referencedColumnName = "ID")
    private Classroom subjectClassroom;

    @OneToMany(mappedBy = "subjectLessons")
    private Set<Lesson> lessons;

    public Subject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
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
