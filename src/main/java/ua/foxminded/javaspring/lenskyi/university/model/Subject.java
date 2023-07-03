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

    @Column(name = "PROFESSOR_ID", insertable = false, updatable = false)
    private Long professorId;

    @Column(name = "CLASSROOM_ID", insertable = false, updatable = false)
    private Long classroomId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(mappedBy = "professorSubject")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLASSROOM_ID", referencedColumnName = "ID")
    private Classroom subjectClassroom;

    @OneToMany(mappedBy = "subjectLessons")
    private Set<Lesson> lessons;
}
