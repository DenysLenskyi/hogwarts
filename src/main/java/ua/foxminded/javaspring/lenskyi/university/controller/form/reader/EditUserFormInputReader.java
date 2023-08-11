package ua.foxminded.javaspring.lenskyi.university.controller.form.reader;

public class EditUserFormInputReader {

    private String roleNameToInclude;
    private String roleNameToExclude;

    public String getRoleNameToInclude() {
        return roleNameToInclude;
    }

    public void setRoleNameToInclude(String roleNameToInclude) {
        this.roleNameToInclude = roleNameToInclude;
    }

    public String getRoleNameToExclude() {
        return roleNameToExclude;
    }

    public void setRoleNameToExclude(String roleNameToExclude) {
        this.roleNameToExclude = roleNameToExclude;
    }
}
