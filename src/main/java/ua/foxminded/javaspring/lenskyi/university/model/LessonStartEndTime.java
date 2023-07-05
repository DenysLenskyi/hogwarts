package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "LESSON_START_END_TIME", schema = "HOGWARTS")
public class LessonStartEndTime {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "START_TIME")
    private LocalTime start;

    @Column(name = "END_TIME")
    private LocalTime end;

    @OneToMany(mappedBy = "lessonStartEndTime")
    private Set<Lesson> lessons;

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