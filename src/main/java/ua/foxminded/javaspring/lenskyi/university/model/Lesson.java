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

    @Column(name = "LESSON_DATE")
    private LocalDate date;

    @Column(name = "START_END_TIME_ID")
    private Long startEndTimeId;

    @Column(name = "SUBJECT_ID")
    private Long subjectId;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "START_END_TIME_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private LessonStartEndTime lessonStartEndTime;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID", insertable = false, updatable = false)
    private Subject subjectLessons;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
    private Group groupLessons;

}
