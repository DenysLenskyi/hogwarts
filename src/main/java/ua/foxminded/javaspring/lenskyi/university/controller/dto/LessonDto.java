package ua.foxminded.javaspring.lenskyi.university.controller.dto;

import java.time.LocalDate;

public class LessonDto {

    private Long id;
    private LocalDate date;
    private LessonTimeDto lessonTimeDto;
    private SubjectDto subjectDto;
    private GroupDto groupDto;

    public LessonDto() {
    }

    public LessonDto(LessonTimeDto lessonTimeDto, SubjectDto subjectDto, GroupDto groupDto) {
        this.lessonTimeDto = lessonTimeDto;
        this.subjectDto = subjectDto;
        this.groupDto = groupDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LessonTimeDto getLessonTimeDto() {
        return lessonTimeDto;
    }

    public void setLessonTimeDto(LessonTimeDto lessonTimeDto) {
        this.lessonTimeDto = lessonTimeDto;
    }

    public SubjectDto getSubjectDto() {
        return subjectDto;
    }

    public void setSubjectDto(SubjectDto subjectDto) {
        this.subjectDto = subjectDto;
    }

    public GroupDto getGroupDto() {
        return groupDto;
    }

    public void setGroupDto(GroupDto groupDto) {
        this.groupDto = groupDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonDto lessonDto)) return false;

        if (getId() != null ? !getId().equals(lessonDto.getId()) : lessonDto.getId() != null) return false;
        return getDate() != null ? getDate().equals(lessonDto.getDate()) : lessonDto.getDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }
}
