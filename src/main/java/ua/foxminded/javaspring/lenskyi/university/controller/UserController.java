package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static ua.foxminded.javaspring.lenskyi.university.controller.DefaultMessage.*;

@Controller
@RequestMapping(USER_ROOT)
public class UserController {

    private UserService userService;
    private GroupService groupService;
    private SubjectService subjectService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public UserController(UserService userService, GroupService groupService,
                          SubjectService subjectService) {
        this.userService = userService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping(STUDENTS_PAGE)
    public String getStudentPage(Model model) {
        model.addAttribute("students", userService.findAllStudent());
        log.info("getStudentPage called");
        return STUDENTS_PAGE;
    }

    @GetMapping("/student/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewStudentPage(Model model) {
        model.addAttribute("studentDto", new UserDto());
        model.addAttribute("groups", groupService.findAll());
        log.info("getNewStudentPage called");
        return CREATE_STUDENT_PAGE_TEMPLATE_NAME;
    }

    @PostMapping("/student")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewStudent(@Valid UserDto userDto, Model model) {
        userService.createStudent(userDto);
        log.info("Student created");
        model.addAttribute("message", CREATED_MESSAGE);
        model.addAttribute("students", userService.findAllStudent());
        return STUDENTS_PAGE;
    }

    @DeleteMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteStudent(@PathVariable("studentId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        userService.deleteById(id);
        log.info("Student deleted");
        model.addAttribute("message", DELETED_MESSAGE);
        model.addAttribute("students", userService.findAllStudent());
        return STUDENTS_PAGE;
    }

    @GetMapping("/student/{studentId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getEditStudentPage(@PathVariable("studentId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        log.info("getEditStudentPage called");
        model.addAttribute("studentDto", userService.findById(id));
        model.addAttribute("groups", groupService.findAll());
        return EDIT_STUDENT_PAGE_TEMPLATE_NAME;
    }

    @PutMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editStudent(@PathVariable("studentId") Long id, @Valid UserDto userDto, Model model) {
        if (userService.existsByUsername(userDto.getUsername()) && !userDto.getUsername()
                .equals(userService.findById(id).getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        userDto.setId(id);
        userService.updateStudent(userDto);
        log.info("Student updated");
        model.addAttribute("message", UPDATED_MESSAGE);
        model.addAttribute("students", userService.findAllStudent());
        return STUDENTS_PAGE;
    }

    @GetMapping(PROFESSORS_PAGE)
    public String getProfessorPage(Model model) {
        model.addAttribute("professorsAndAdmins", userService.findAllProfessorAndAdmin());
        log.info("getProfessorPage called");
        return PROFESSORS_PAGE;
    }

    @GetMapping("/professor/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewProfessorPage(Model model) {
        model.addAttribute("professorForm", new ProfessorForm());
        model.addAttribute("freeSubjects", subjectService.findAllSubjectsWithNoProfessor());
        log.info("getNewProfessorPage called");
        return CREATE_PROFESSOR_PAGE_TEMPLATE_NAME;
    }

    @PostMapping("/professor")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewProfessor(@Valid ProfessorForm professorForm,
                                     Model model) {
        userService.createProfessor(professorForm);
        log.info("Professor created");
        model.addAttribute("message", CREATED_MESSAGE);
        model.addAttribute("professorsAndAdmins", userService.findAllProfessorAndAdmin());
        return PROFESSORS_PAGE;
    }

    @DeleteMapping("/professor/{professorId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteProfessor(@PathVariable("professorId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        userService.deleteById(id);
        log.info("Professor deleted");
        model.addAttribute("message", CREATED_MESSAGE);
        model.addAttribute("professorsAndAdmins", userService.findAllProfessorAndAdmin());
        return PROFESSORS_PAGE;
    }

    @GetMapping("/professor/{professorId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getEditProfessorPage(@PathVariable("professorId") Long id, Model model) {
        if (!userService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        log.info("getEditProfessorPage called");
        model.addAttribute("professorForm", userService.createProfessorFormDto(id));
        model.addAttribute("freeSubjects", subjectService.findAllSubjectsWithNoProfessor());
        return EDIT_PROFESSOR_PAGE_TEMPLATE_NAME;
    }

    @PutMapping("/professor/{professorId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editProfessor(@PathVariable("professorId") Long id, @Valid ProfessorForm professorForm,
                                Model model) {
        if (userService.existsByUsername(professorForm.getUsername()) && !professorForm.getUsername()
                .equals(userService.findById(id).getUsername())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        professorForm.setId(id);
        userService.updateProfessor(professorForm);
        log.info("Professor updated");
        model.addAttribute("message", UPDATED_MESSAGE);
        model.addAttribute("professorsAndAdmins", userService.findAllProfessorAndAdmin());
        return PROFESSORS_PAGE;
    }
}