package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class GroupDto {

    private Long id;
    @NotBlank
    @NotNull
    private String name;
    private long numStudentsInGroup;
    private Set<UserDto> users;
    private Set<LessonDto> lessons;

    public GroupDto() {
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

    public long getNumStudentsInGroup() {
        return numStudentsInGroup;
    }

    public void setNumStudentsInGroup(long numStudentsInGroup) {
        this.numStudentsInGroup = numStudentsInGroup;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    public Set<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonDto> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDto groupDto)) return false;

        if (getNumStudentsInGroup() != groupDto.getNumStudentsInGroup()) return false;
        if (getId() != null ? !getId().equals(groupDto.getId()) : groupDto.getId() != null) return false;
        return getName() != null ? getName().equals(groupDto.getName()) : groupDto.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (int) (getNumStudentsInGroup() ^ (getNumStudentsInGroup() >>> 32));
        return result;
    }
}
