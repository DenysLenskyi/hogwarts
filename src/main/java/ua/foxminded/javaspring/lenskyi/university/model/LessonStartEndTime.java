package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "LESSON_START_END_TIME", schema = "HOGWARTS")
public class LessonStartEndTime {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_start_end_time_id_seq")
    @SequenceGenerator(name = "lesson_start_end_time_id_seq",
            sequenceName = "LESSON_START_END_TIME_ID_SEQ",
            schema = "HOGWARTS",
            allocationSize = 1)
    private Long id;

    @Column(name = "START_TIME", nullable = false, unique = true)
    private LocalTime start;

    @Column(name = "END_TIME", nullable = false, unique = true)
    private LocalTime end;

    public LessonStartEndTime() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}