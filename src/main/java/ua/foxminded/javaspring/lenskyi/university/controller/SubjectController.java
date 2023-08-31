package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    private static final String REDIRECT_SUBJECT_PAGE = "redirect:/subject/all";
    private SubjectService subjectService;
    private ClassroomService classroomService;
    private UserService userService;

    public SubjectController(SubjectService subjectService, ClassroomService classroomService, UserService userService) {
        this.subjectService = subjectService;
        this.classroomService = classroomService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getSubjectPage(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects-db-overview";
    }

    @GetMapping("/{subjectId}/edit-page")
    public String showEditSubjectForm(@PathVariable("subjectId") Long id, Model model) {
        if (!subjectService.doesSubjectExistById(id)) {
            return "error/404";
        } else {
            SubjectDto subjectDto = subjectService.findById(id);
            model.addAttribute("subjectDto", subjectDto);
            model.addAttribute("freeClassrooms", classroomService.findAllFreeClassrooms());
            model.addAttribute("freeProfessors", userService.findAllProfessorsWithNoSubject());
            return "forms/edit-subject-form";
        }
    }

    @PutMapping("/{subjectId}")
    public String editSubject(@PathVariable("subjectId") Long id, SubjectDto subjectDto) {
        if (subjectService.existsByName(subjectDto.getName()) && !subjectDto.getName()
                .equals(subjectService.findById(id).getName())) {
            return "error/400";
        } else {
            subjectDto.setId(id);
            subjectService.updateSubjectFromSubjectDto(subjectDto);
            return REDIRECT_SUBJECT_PAGE;
        }
    }

    @GetMapping("/create-subject-page")
    public String showCreateNewSubjectForm(Model model) {
        model.addAttribute("subjectDto", new SubjectDto());
        model.addAttribute("freeClassrooms", classroomService.findAllFreeClassrooms());
        model.addAttribute("freeProfessors", userService.findAllProfessorsWithNoSubject());
        return "forms/create-subject";
    }

    @PostMapping
    public String createNewSubject(SubjectDto subjectDto) {
        if (subjectService.existsByName(subjectDto.getName())) {
            return "error/400";
        } else {
            subjectService.createNewSubjectFromSubjectDto(subjectDto);
            return REDIRECT_SUBJECT_PAGE;
        }
    }

    @DeleteMapping("/{subjectId}")
    public String deleteSubject(@PathVariable("subjectId") Long id) {
        if (!subjectService.doesSubjectExistById(id)) {
            return "error/404";
        } else {
            subjectService.deleteSubjectById(id);
            return REDIRECT_SUBJECT_PAGE;
        }
    }
}

// minervamcgonagall