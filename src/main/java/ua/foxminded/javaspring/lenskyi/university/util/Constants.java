package ua.foxminded.javaspring.lenskyi.university.util;

public abstract class Constants {

    public static final String LOGIN_PAGE_TEMPLATE_NAME = "login";
    public static final String README_PAGE_TEMPLATE_NAME = "readme-page";
    public static final String REDIRECT_TO = "redirect:";


    // user
    public static final String USER_ROOT = "/user";
    public static final String STUDENTS_PAGE = "/students-page";
    public static final String PROFESSORS_PAGE = "/professors-page";
    public static final String REDIRECT_TO_STUDENTS_PAGE = REDIRECT_TO + USER_ROOT + STUDENTS_PAGE;
    public static final String REDIRECT_TO_PROFESSORS_PAGE = REDIRECT_TO + USER_ROOT + PROFESSORS_PAGE;
    public static final String STUDENT_CREATION_PAGE = "forms/student-creation-page"; //example of naming
    public static final String CREATE_STUDENT_PAGE_TEMPLATE_NAME = "forms/create-student-form"; // forms/student-creation-page
    public static final String EDIT_STUDENT_PAGE_TEMPLATE_NAME = "forms/edit-student-form"; // forms/student-edit-page
    public static final String CREATE_PROFESSOR_PAGE_TEMPLATE_NAME = "forms/create-professor-form"; // forms/professor-creation-page
    public static final String EDIT_PROFESSOR_PAGE_TEMPLATE_NAME = "forms/edit-professor-form"; // forms/professor-edit-page


    // subject
    public static final String REDIRECT_SUBJECT_PAGE = "redirect:/subject/all";
    public static final String SUBJECT_PAGE_TEMPLATE_NAME = "subjects-page";
    public static final String EDIT_SUBJECT_TEMPLATE_NAME = "forms/edit-subject-form";
    public static final String CREATE_SUBJECT_TEMPLATE_NAME = "forms/create-subject-form";


    // group
    public static final String REDIRECT_GROUP_PAGE = "redirect:/group/all";
    public static final String GROUP_PAGE_TEMPLATE_NAME = "groups-page";
    public static final String CREATE_GROUP_TEMPLATE_NAME = "forms/create-group-form";
    public static final String EDIT_GROUP_TEMPLATE_NAME = "forms/edit-group-form";


    //classroom
    public static final String CREATE_CLASSROOM_TEMPLATE_NAME = "forms/create-classroom-form";
    public static final String EDIT_CLASSROOM_TEMPLATE_NAME = "forms/edit-classroom-form";
    public static final String CLASSROOM_PAGE_TEMPLATE_NAME = "classrooms-page";
    public static final String REDIRECT_CLASSROOM_PAGE = "redirect:/classroom/all";
    public static final String REDIRECT_LESSON_PAGE = "redirect:/lesson/all";


    //lesson
    public static final String LESSON_PAGE_TEMPLATE_NAME = "lessons-page";
    public static final String CREATE_LESSON_PAGE_TEMPLATE_NAME = "forms/create-lesson-form";


    // error
    public static final String ERROR_400_TEMPLATE_NAME = "error/400";
    public static final String ERROR_403_TEMPLATE_NAME = "error/403";
    public static final String ERROR_404_TEMPLATE_NAME = "error/404";
    public static final String ERROR_500_TEMPLATE_NAME = "error/500";
    public static final String ERROR_TEMPLATE_NAME = "error/error";
}
