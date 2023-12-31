package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "LESSON", schema = "HOGWARTS")
public class Lesson {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LESSON_DATE", nullable = false)
    private LocalDate date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "START_END_TIME_ID", referencedColumnName = "ID")
    private LessonTime lessonTime;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public Lesson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LessonTime getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(LessonTime lessonTime) {
        this.lessonTime = lessonTime;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subjectLessons) {
        this.subject = subjectLessons;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group groupLessons) {
        this.group = groupLessons;
    }
}
