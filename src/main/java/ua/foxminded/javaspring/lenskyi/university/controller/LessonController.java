package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.*;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonTimeService;
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
    private final LessonTimeService lessonTimeService;
    private final GroupService groupService;
    private final SubjectService subjectService;

    public LessonController(LessonService lessonService, LessonTimeService lessonTimeService,
                            GroupService groupService, SubjectService subjectService) {
        this.lessonService = lessonService;
        this.lessonTimeService = lessonTimeService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    public String getLessonPage(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return LESSON_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/create-lesson-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getCreateLessonPage(Model model) {
        model.addAttribute("lessonDto", new LessonDto(new LessonTimeDto(),
                new SubjectDto(new UserDto(), new ClassroomDto()),
                new GroupDto()));
        model.addAttribute("lessonTimeList", lessonTimeService.findAllDto());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("subjects", subjectService.findAllDto());
        return CREATE_LESSON_PAGE_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewLesson(@Valid LessonDto lessonDto, Model model) {
        List<String> errorMessages = new ArrayList<>();
        if (lessonService.isBusyByGroup(lessonDto.getDate(), lessonDto.getLessonTimeDto(),
                lessonDto.getGroupDto())) {
            errorMessages.add(BUSY_GROUP_ERROR_MESSAGE);
        }
        if (lessonService.isBusyBySubject(lessonDto.getDate(), lessonDto.getLessonTimeDto(),
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

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteLesson(@PathVariable("lessonId") Long id) {
        if (!lessonService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        lessonService.deleteById(id);
        return REDIRECT_LESSON_PAGE;
    }
}

// minervamcgonagall