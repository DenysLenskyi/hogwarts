package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.form.ProfessorForm;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private GroupService groupService;
    private SubjectService subjectService;

    public UserController(UserService userService, GroupService groupService,
                          SubjectService subjectService) {
        this.userService = userService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping("/student-page")
    public String getStudentPage(Model model) {
        model.addAttribute("students", userService.findAllStudent());
        return STUDENTS_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/student/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewStudentPage(Model model) {
        model.addAttribute("studentDto", new UserDto());
        model.addAttribute("groups", groupService.findAll());
        return CREATE_STUDENT_PAGE_TEMPLATE_NAME;
    }

    @PostMapping("/student")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewStudent(UserDto userDto) {
        userService.createStudent(userDto);
        return REDIRECT_STUDENT_PAGE;
    }

    @DeleteMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteStudent(@PathVariable("studentId") Long id) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        userService.deleteById(id);
        return REDIRECT_STUDENT_PAGE;
    }

    @GetMapping("/student/{studentId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getEditStudentPage(@PathVariable("studentId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        model.addAttribute("studentDto", userService.findById(id));
        model.addAttribute("groups", groupService.findAll());
        return EDIT_STUDENT_PAGE_TEMPLATE_NAME;
    }

    @PutMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editStudent(@PathVariable("studentId") Long id, UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername()) && !userDto.getUsername()
                .equals(userService.findById(id).getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        userDto.setId(id);
        userService.updateStudent(userDto);
        return REDIRECT_STUDENT_PAGE;
    }

    @GetMapping("/professor-page")
    public String getProfessorPage(Model model) {
        model.addAttribute("professorsAndAdmins", userService.findAllProfessorAndAdmin());
        return PROFESSORS_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/professor/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewProfessorPage(Model model) {
        model.addAttribute("professorForm", new ProfessorForm());
        model.addAttribute("freeSubjects", subjectService.findAllSubjectsWithNoProfessor());
        return CREATE_PROFESSOR_PAGE_TEMPLATE_NAME;
    }

    @PostMapping("/professor")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewProfessor(ProfessorForm professorForm) {
        userService.createProfessor(professorForm);
        return REDIRECT_PROFESSOR_PAGE;
    }

    @DeleteMapping("/professor/{professorId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteProfessor(@PathVariable("professorId") Long id) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        userService.deleteById(id);
        return REDIRECT_PROFESSOR_PAGE;
    }

    @GetMapping("/professor/{professorId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getEditProfessorPage(@PathVariable("professorId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        model.addAttribute("professorForm", userService.createProfessorFormDto(id));
        model.addAttribute("freeSubjects", subjectService.findAllSubjectsWithNoProfessor());
        return EDIT_PROFESSOR_PAGE_TEMPLATE_NAME;
    }

    @PutMapping("/professor/{professorId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editProfessor(@PathVariable("professorId") Long id, ProfessorForm professorForm) {
        if (userService.existsByUsername(professorForm.getUsername()) && !professorForm.getUsername()
                .equals(userService.findById(id).getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        professorForm.setId(id);
        userService.updateProfessor(professorForm);
        return REDIRECT_PROFESSOR_PAGE;
    }
}