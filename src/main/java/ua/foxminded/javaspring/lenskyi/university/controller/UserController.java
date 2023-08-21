package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.controller.form.reader.EditUserFormInputReader;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/all"})
    public String getUserPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users-db-overview";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        if (!userService.doesUserExistById(id)) {
            return "error/404";
        } else {
            UserDto userDto = userService.getUserDtoByUserId(id);
            EditUserFormInputReader inputReader = new EditUserFormInputReader();
            model.addAttribute("user", userDto);
            model.addAttribute("inputReader", inputReader);
            model.addAttribute("pageTitle", "Change Role For User");

            return "forms/edit-user-form";
        }
    }

    @PutMapping(value = "/edit/{id}")
    public String editUser(EditUserFormInputReader inputReader, @PathVariable("id") Long id) {
        try {
            if (inputReader.getCheckboxSelectedValues() != null)
                userService.updateRolesFromArray(userService.findById(id), inputReader.getCheckboxSelectedValues());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/all";
    }
    // minervamcgonagall
}