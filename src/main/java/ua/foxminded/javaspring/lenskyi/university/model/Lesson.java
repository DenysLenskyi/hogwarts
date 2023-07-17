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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "START_END_TIME_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private LessonStartEndTime lessonStartEndTime;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID", insertable = false, updatable = false)
    private Subject subjectOfTheLesson;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
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

    public LessonStartEndTime getLessonStartEndTime() {
        return lessonStartEndTime;
    }

    public void setLessonStartEndTime(LessonStartEndTime lessonStartEndTime) {
        this.lessonStartEndTime = lessonStartEndTime;
    }

    public Subject getSubjectOfTheLesson() {
        return subjectOfTheLesson;
    }

    public void setSubjectOfTheLesson(Subject subjectLessons) {
        this.subjectOfTheLesson = subjectLessons;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group groupLessons) {
        this.group = groupLessons;
    }
}
