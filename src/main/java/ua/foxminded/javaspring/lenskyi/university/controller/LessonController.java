package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.*;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonStartEndTimeService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.util.ArrayList;
import java.util.List;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private static final String BUSY_GROUP_ERROR_MESSAGE =
            "The group you've chosen has another lesson at the same date and time";
    private static final String BUSY_SUBJECT_ERROR_MESSAGE =
            "The subject you've chosen has another lesson at the same date and time";
    private final LessonService lessonService;
    private final LessonStartEndTimeService lessonStartEndTimeService;
    private final GroupService groupService;
    private final SubjectService subjectService;

    public LessonController(LessonService lessonService, LessonStartEndTimeService lessonStartEndTimeService,
                            GroupService groupService, SubjectService subjectService) {
        this.lessonService = lessonService;
        this.lessonStartEndTimeService = lessonStartEndTimeService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    public String getLessonPage(Model model) {
        model.addAttribute("lessons", lessonService.findAllLessonDto());
        return LESSON_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/create-lesson-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getCreateLessonPage(Model model) {
        model.addAttribute("lessonDto", new LessonDto(new LessonStartEndTimeDto(),
                new SubjectDto(new UserDto(), new ClassroomDto()),
                new GroupDto()));
        model.addAttribute("startEndTimeList", lessonStartEndTimeService.findAllDto());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("subjects", subjectService.findAllDto());
        return CREATE_LESSON_PAGE_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewLesson(@Valid LessonDto lessonDto, Model model) {
        List<String> errorMessages = new ArrayList<>();
        if (lessonService.isBusyByGroup(lessonDto.getDate(), lessonDto.getLessonStartEndTimeDto(),
                lessonDto.getGroupDto())) {
            errorMessages.add(BUSY_GROUP_ERROR_MESSAGE);
        }
        if (lessonService.isBusyBySubject(lessonDto.getDate(), lessonDto.getLessonStartEndTimeDto(),
                lessonDto.getSubjectDto())) {
            errorMessages.add(BUSY_SUBJECT_ERROR_MESSAGE);
        }
        model.addAttribute("errorMessages", errorMessages);
        if (!errorMessages.isEmpty()) {
            return ERROR_400_TEMPLATE_NAME;
        }
        lessonService.createLesson(lessonDto);
        return REDIRECT_LESSON_PAGE;
    }
}

// minervamcgonagall