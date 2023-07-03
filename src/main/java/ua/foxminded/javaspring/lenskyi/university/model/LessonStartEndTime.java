package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "LESSON_START_END_TIME", schema = "HOGWARTS")
public class LessonStartEndTime {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="START_TIME")
    private LocalTime start;

    @Column(name ="END_TIME")
    private LocalTime end;

    @OneToOne(mappedBy = "lessonStartEndTime")
    private Lesson lesson;
}