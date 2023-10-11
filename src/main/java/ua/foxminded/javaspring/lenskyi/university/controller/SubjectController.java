package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;
import static ua.foxminded.javaspring.lenskyi.university.controller.DefaultMessage.*;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    private static final String NOT_UNIQUE_SUBJECT_NAME_ERROR_MESSAGE =
            "Subject name should be unique. We do not repeat ourselves in Hogwarts. This name is in use already: ";
    private SubjectService subjectService;
    private ClassroomService classroomService;
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public SubjectController(SubjectService subjectService, ClassroomService classroomService, UserService userService) {
        this.subjectService = subjectService;
        this.classroomService = classroomService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getSubjectPage(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        log.info("getSubjectPage called");
        return SUBJECT_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/{subjectId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin', 'professor')")
    public String getEditSubjectPage(@PathVariable("subjectId") Long id, Model model) {
        if (!subjectService.doesSubjectExistById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        SubjectDto subjectDto = subjectService.findById(id);
        log.info("getEditSubjectPage called");
        model.addAttribute("subjectDto", subjectDto);
        model.addAttribute("freeClassrooms", classroomService.findAllFreeClassrooms());
        model.addAttribute("freeProfessors", userService.findAllProfessorWithNoSubject());
        return EDIT_SUBJECT_TEMPLATE_NAME;
    }

    @PutMapping("/{subjectId}")
    @PreAuthorize("hasAnyAuthority('admin', 'professor')")
    public String editSubject(@PathVariable("subjectId") Long id, @Valid SubjectDto subjectDto, Model model) {
        if (subjectService.existsByName(subjectDto.getName()) && !subjectDto.getName()
                .equals(subjectService.findById(id).getName())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        subjectDto.setId(id);
        subjectService.updateSubject(subjectDto);
        log.info("Subject edited");
        model.addAttribute("message", UPDATED_MESSAGE);
        model.addAttribute("subjects", subjectService.findAll());
        return SUBJECT_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewSubjectPage(Model model) {
        model.addAttribute("subjectDto", new SubjectDto());
        model.addAttribute("freeClassrooms", classroomService.findAllFreeClassrooms());
        model.addAttribute("freeProfessors", userService.findAllProfessorWithNoSubject());
        log.info("getNewSubjectPage called");
        return CREATE_SUBJECT_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewSubject(@Valid SubjectDto subjectDto, Model model) {
        if (subjectService.existsByName(subjectDto.getName())) {
            model.addAttribute("message", NOT_UNIQUE_SUBJECT_NAME_ERROR_MESSAGE);
            model.addAttribute("subjectName", subjectDto.getName());
            return ERROR_400_TEMPLATE_NAME;
        }
        subjectService.createNewSubject(subjectDto);
        log.info("Subject created");
        model.addAttribute("message", CREATED_MESSAGE);
        model.addAttribute("subjects", subjectService.findAll());
        return SUBJECT_PAGE_TEMPLATE_NAME;
    }

    @DeleteMapping("/{subjectId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteSubject(@PathVariable("subjectId") Long id, Model model) {
        if (!subjectService.doesSubjectExistById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        subjectService.deleteSubjectById(id);
        log.info("Subject deleted");
        model.addAttribute("message", DELETED_MESSAGE);
        model.addAttribute("subjects", subjectService.findAll());
        return SUBJECT_PAGE_TEMPLATE_NAME;
    }
}