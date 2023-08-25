package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import ua.foxminded.javaspring.lenskyi.university.model.Classroom;
import ua.foxminded.javaspring.lenskyi.university.model.Lesson;
import ua.foxminded.javaspring.lenskyi.university.model.User;

import java.util.Set;

public class SubjectDto {

    private Long id;
    private String name;
    private String description;
    private User user;
    private Classroom classroom;
    private Set<Lesson> lessons;

    public SubjectDto() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom subjectClassroom) {
        this.classroom = subjectClassroom;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectDto that)) return false;

        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        if (getClassroom() != null ? !getClassroom().equals(that.getClassroom()) : that.getClassroom() != null)
            return false;
        return getLessons() != null ? getLessons().equals(that.getLessons()) : that.getLessons() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getClassroom() != null ? getClassroom().hashCode() : 0);
        result = 31 * result + (getLessons() != null ? getLessons().hashCode() : 0);
        return result;
    }
}
