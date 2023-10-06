package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;
import static ua.foxminded.javaspring.lenskyi.university.controller.DefaultMessage.*;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private static final String BUSY_GROUP_ERROR_MESSAGE =
            "The group you've chosen has another lesson at the same date and time";
    private static final String BUSY_SUBJECT_ERROR_MESSAGE =
            "The subject you've chosen has another lesson at the same date and time";
    private static int defaultInitialStartPage = 1;
    private static int defaultInitialPageSize = 10;
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
    public String getLessonPage(Model model, @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(defaultInitialStartPage);
        int pageSize = size.orElse(defaultInitialPageSize);
        Page<LessonDto> lessonDtoPage = lessonService.findAllPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("lessonsPage", lessonDtoPage);
        int totalPages = lessonDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return LESSON_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewLessonPage(Model model) {
        model.addAttribute("lessonDto", new LessonDto(new LessonTimeDto(),
                new SubjectDto(new UserDto(), new ClassroomDto()),
                new GroupDto()));
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
        model.addAttribute("message", CREATED_MESSAGE);
        return getLessonPage(model, Optional.of(defaultInitialStartPage), Optional.of(defaultInitialPageSize));
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteLesson(@PathVariable("lessonId") Long id, Model model) {
        if (!lessonService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        lessonService.deleteById(id);
        model.addAttribute("message", DELETED_MESSAGE);
        return getLessonPage(model, Optional.of(defaultInitialStartPage), Optional.of(defaultInitialPageSize));
    }
}