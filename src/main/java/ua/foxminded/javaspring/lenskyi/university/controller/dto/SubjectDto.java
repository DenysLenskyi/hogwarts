package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class SubjectDto {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
    private String description;
    private UserDto userDto;
    private ClassroomDto classroomDto;
    private Set<LessonDto> lessonsDto;

    public SubjectDto() {
    }

    public SubjectDto(UserDto userDto, ClassroomDto classroomDto) {
        this.userDto = userDto;
        this.classroomDto = classroomDto;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ClassroomDto getClassroomDto() {
        return classroomDto;
    }

    public void setClassroomDto(ClassroomDto classroomDto) {
        this.classroomDto = classroomDto;
    }

    public Set<LessonDto> getLessonsDto() {
        return lessonsDto;
    }

    public void setLessonsDto(Set<LessonDto> lessonsDto) {
        this.lessonsDto = lessonsDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectDto that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}