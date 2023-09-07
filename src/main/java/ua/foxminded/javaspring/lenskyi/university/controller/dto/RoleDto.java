package ua.foxminded.javaspring.lenskyi.university.controller.dto;


import java.util.Set;

public class RoleDto {

    private Long id;
    private String name;
    private Set<UserDto> users;

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

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleDto roleDto)) return false;

        if (getId() != null ? !getId().equals(roleDto.getId()) : roleDto.getId() != null) return false;
        return getName() != null ? getName().equals(roleDto.getName()) : roleDto.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}