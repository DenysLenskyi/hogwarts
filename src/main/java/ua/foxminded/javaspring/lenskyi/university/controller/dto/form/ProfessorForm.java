package ua.foxminded.javaspring.lenskyi.university.controller.dto.form;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;

public class ProfessorForm extends UserDto {

    private String subjectName;
    private boolean isAdmin;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessorForm that)) return false;
        if (!super.equals(o)) return false;

        if (isAdmin() != that.isAdmin()) return false;
        return getSubjectName() != null ? getSubjectName().equals(that.getSubjectName()) : that.getSubjectName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getSubjectName() != null ? getSubjectName().hashCode() : 0);
        result = 31 * result + (isAdmin() ? 1 : 0);
        return result;
    }
}