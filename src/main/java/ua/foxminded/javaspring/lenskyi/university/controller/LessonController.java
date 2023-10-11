package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.*;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;
import ua.foxminded.javaspring.lenskyi.university.service.LessonTimeService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;
import static ua.foxminded.javaspring.lenskyi.university.controller.DefaultMessage.*;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private static final String BUSY_GROUP_ERROR_MESSAGE =
            "The group you've chosen has another lesson at the same date and time";
    private static final String BUSY_SUBJECT_ERROR_MESSAGE =
            "The subject you've chosen has another lesson at the same date and time";
    private static final int DEFAULT_INITIAL_PAGE_SIZE = 10;
    private final LessonService lessonService;
    private final LessonTimeService lessonTimeService;
    private final GroupService groupService;
    private final SubjectService subjectService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public LessonController(LessonService lessonService, LessonTimeService lessonTimeService,
                            GroupService groupService, SubjectService subjectService) {
        this.lessonService = lessonService;
        this.lessonTimeService = lessonTimeService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    public String getLessonPage(Model model,
                                @RequestParam(defaultValue = "0") int page) {
        Page<LessonDto> lessonDtoPage = lessonService.findAllPaginated(PageRequest.of(page, DEFAULT_INITIAL_PAGE_SIZE));
        log.info("getLessonPage called");
        model.addAttribute("lessonsPage", lessonDtoPage);
        model.addAttribute("currentPage", page);
        return LESSON_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/all/by-date")
    public String getLessonPageByDate(Model model,
                                      @RequestParam(value = "date", required = false) LocalDate date,
                                      @RequestParam(value = "dateStart", required = false) LocalDate dateStart,
                                      @RequestParam(value = "dateEnd", required = false) LocalDate dateEnd) {
        List<LessonDto> lessons = new ArrayList<>();
        if (date != null) {
            lessons = lessonService.findAllByDate(date);
        }
        if ((dateStart != null) && (dateEnd != null)) {
            lessons = lessonService.findAllByDateBetween(dateStart, dateEnd);
        }
        log.info("getLessonPageByDate called");
        model.addAttribute("lessons", lessons);
        return LESSON_PAGE_BY_DATE_TEMPLATE_NAME;
    }

    @GetMapping("/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewLessonPage(Model model) {
        model.addAttribute("lessonDto", new LessonDto(new LessonTimeDto(),
                new SubjectDto(new UserDto(), new ClassroomDto()),
                new GroupDto()));
        log.info("getNewLessonPage called");
        model.addAttribute("lessonTimeList", lessonTimeService.findAllDto());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
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
        log.info("Lesson created");
        model.addAttribute("message", CREATED_MESSAGE);
        return getLessonPage(model, 0);
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteLesson(@PathVariable("lessonId") Long id, Model model) {
        if (!lessonService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        lessonService.deleteById(id);
        log.info("Lesson deleted");
        model.addAttribute("message", DELETED_MESSAGE);
        return getLessonPage(model, 0);
    }
}