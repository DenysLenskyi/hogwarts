package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.SubjectDto;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;
import ua.foxminded.javaspring.lenskyi.university.service.SubjectService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

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
        return "subjects-db-overview";
    }

    @GetMapping("/edit/{id}")
    public String showEditSubjectForm(@PathVariable("id") Long id, Model model) {
        if (!subjectService.doesSubjectExistById(id)) {
            return "error/404";
        } else {
            SubjectDto subjectDto = subjectService.findSubjectDtoById(id);
            model.addAttribute("subjectDto", subjectDto);
            model.addAttribute("freeClassrooms", classroomService.findAllFreeClassrooms());
            model.addAttribute("freeProfessors", userService.findAllProfessorsWithNoSubject());
            return "forms/edit-subject-form";
        }
    }

//    @GetMapping("/edit/{id}") //to do add test for this
//    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
//        if (!userService.doesUserExistById(id)) {
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
}