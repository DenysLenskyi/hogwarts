package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import ua.foxminded.javaspring.lenskyi.university.model.Subject;

public class ClassroomDto {

    private Long id;
    private String name;
    private String description;
    private Subject subject;

    public ClassroomDto() {
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}