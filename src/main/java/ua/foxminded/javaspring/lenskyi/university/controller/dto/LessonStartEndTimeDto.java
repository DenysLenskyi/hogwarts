package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import java.time.LocalTime;

public class LessonStartEndTimeDto {

    private Long id;
    private LocalTime start;
    private LocalTime end;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonStartEndTimeDto that)) return false;

        if (!getId().equals(that.getId())) return false;
        if (!getStart().equals(that.getStart())) return false;
        return getEnd().equals(that.getEnd());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getStart().hashCode();
        result = 31 * result + getEnd().hashCode();
        return result;
    }
}