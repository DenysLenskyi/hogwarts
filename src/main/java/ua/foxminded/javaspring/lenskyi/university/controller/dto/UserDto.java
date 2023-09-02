package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.Subject;

import java.util.Set;

public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<RoleDto> roles;
    private Group group;
    private Subject subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public String getRolesToString() {
        StringBuilder output = new StringBuilder();
        final String emptyString = "";
        this.getRoles().forEach(r ->
                output.append(r.getName())
                        .append(',')
                        .append(' ')
        );
        if (this.getRoles().isEmpty()) {
            return emptyString;
        } else {
            return output.substring(0, output.length() - 2);
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;

        if (getId() != null ? !getId().equals(userDto.getId()) : userDto.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(userDto.getFirstName()) : userDto.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(userDto.getLastName()) : userDto.getLastName() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(userDto.getUsername()) : userDto.getUsername() != null)
            return false;
        return getPassword() != null ? getPassword().equals(userDto.getPassword()) : userDto.getPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }
}