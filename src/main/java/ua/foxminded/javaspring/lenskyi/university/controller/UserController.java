package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
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

    @GetMapping("/student")
    public String getStudentPage(Model model) {
        model.addAttribute("students", userService.findAllStudents());
        return STUDENTS_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/student/create-student-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getCreateStudentPage(Model model) {
        model.addAttribute("studentDto", new UserDto());
        model.addAttribute("groups", groupService.findAll());
        return CREATE_STUDENT_PAGE_TEMPLATE_NAME;
    }

    @PostMapping("/student")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewStudent(@Valid UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            userService.createStudentFromUserDto(userDto);
            return REDIRECT_STUDENT_PAGE;
        }
    }

    @DeleteMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteStudent(@PathVariable("studentId") Long id) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            userService.deleteById(id);
            return REDIRECT_STUDENT_PAGE;
        }
    }

    @GetMapping("/student/{studentId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String showEditStudentForm(@PathVariable("studentId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            UserDto studentDto = userService.findById(id);
            model.addAttribute("studentDto", studentDto);
            model.addAttribute("groups", groupService.findAll());
            return EDIT_STUDENT_PAGE_TEMPLATE_NAME;
        }
    }

    @PutMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editStudent(@PathVariable("studentId") Long id, UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername()) && !userDto.getUsername()
                .equals(userService.findById(id).getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            userDto.setId(id);
            userService.updateStudentFromUserDto(userDto);
            return REDIRECT_STUDENT_PAGE;
        }
    }

    @GetMapping("/professor")
    public String getProfessorPage(Model model) {
        model.addAttribute("professorsAndAdmins", userService.findAllProfessorsAndAdmins());
        return PROFESSORS_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/professor/create-professor-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getCreateProfessorPage(Model model) {
        model.addAttribute("professorForm", new ProfessorForm());
        model.addAttribute("freeSubjects", subjectService.findAllSubjectsWithNoProfessor());
        return CREATE_PROFESSOR_PAGE_TEMPLATE_NAME;
    }

    @PostMapping("/professor")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewProfessor(ProfessorForm professorForm) {
        if (userService.existsByUsername(professorForm.getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            userService.createProfessorFromProfessorForm(professorForm);
            return REDIRECT_PROFESSOR_PAGE;
        }
    }

    @DeleteMapping("/professor/{professorId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteProfessor(@PathVariable("professorId") Long id) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            userService.deleteById(id);
            return REDIRECT_PROFESSOR_PAGE;
        }
    }

    @GetMapping("/professor/{professorId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String showEditProfessorForm(@PathVariable("professorId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            ProfessorForm professorForm = userService.createAndFillProfessorFormByUserId(id);
            model.addAttribute("professorForm", professorForm);
            model.addAttribute("freeSubjects", subjectService.findAllSubjectsWithNoProfessor());
            return EDIT_PROFESSOR_PAGE_TEMPLATE_NAME;
        }
    }

    @PutMapping("/professor/{professorId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editProfessor(@PathVariable("professorId") Long id, ProfessorForm professorForm) {
        if (userService.existsByUsername(professorForm.getUsername()) && !professorForm.getUsername()
                .equals(userService.findById(id).getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            professorForm.setId(id);
            userService.updateProfessorFromProfessorForm(professorForm);
            return REDIRECT_PROFESSOR_PAGE;
        }
    }

    // minervamcgonagall
}