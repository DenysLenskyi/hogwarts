package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
@RequestMapping("/subject")
public class SubjectController {

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
        return SUBJECT_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/{subjectId}/edit-page")
    public String showEditSubjectForm(@PathVariable("subjectId") Long id, Model model) {
        if (!subjectService.doesSubjectExistById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            SubjectDto subjectDto = subjectService.findById(id);
            model.addAttribute("subjectDto", subjectDto);
            model.addAttribute("freeClassrooms", classroomService.findAllFreeClassrooms());
            model.addAttribute("freeProfessors", userService.findAllProfessorsWithNoSubject());
            return EDIT_SUBJECT_TEMPLATE_NAME;
        }
    }

    @PutMapping("/{subjectId}")
    public String editSubject(@PathVariable("subjectId") Long id, SubjectDto subjectDto) {
        if (subjectService.existsByName(subjectDto.getName()) && !subjectDto.getName()
                .equals(subjectService.findById(id).getName())) {
            return ERROR_400_TEMPLATE_NAME;
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
        return CREATE_SUBJECT_TEMPLATE_NAME;
    }

    @PostMapping
    public String createNewSubject(SubjectDto subjectDto) {
        if (subjectService.existsByName(subjectDto.getName())) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            subjectService.createNewSubjectFromSubjectDto(subjectDto);
            return REDIRECT_SUBJECT_PAGE;
        }
    }

    @DeleteMapping("/{subjectId}")
    public String deleteSubject(@PathVariable("subjectId") Long id) {
        if (!subjectService.doesSubjectExistById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            subjectService.deleteSubjectById(id);
            return REDIRECT_SUBJECT_PAGE;
        }
    }
}

// minervamcgonagall