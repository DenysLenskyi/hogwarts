package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
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

    public UserController(UserService userService, GroupService groupService, SubjectService subjectService) {
        this.userService = userService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping(value = {"", "/all"})
    public String getUserPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users-db-overview";
    }

//    @GetMapping("/edit/{id}") //to do add test for this
//    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
//        if (!userService.existsById(id)) {
//            return "error/404";
//        } else {
//            UserDto userDto = userService.getUserDtoByUserId(id);
//            EditUserFormInputReader inputReader = new EditUserFormInputReader();
//            model.addAttribute("user", userDto);
//            model.addAttribute("inputReader", inputReader);
//            model.addAttribute("pageTitle", "Change Role For User");
//
//            return "forms/edit-user-form";
//        }
//    }
//
//    @PutMapping(value = "/edit/{id}")
//    public String editUser(EditUserFormInputReader inputReader, @PathVariable("id") Long id) {
//        try {
//            if (inputReader.getCheckboxSelectedValues() != null)
//                userService.updateRolesFromArray(userService.findById(id), inputReader.getCheckboxSelectedValues());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "redirect:/user/all";
//    }

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

    // minervamcgonagall
}