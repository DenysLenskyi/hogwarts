package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.controller.form.reader.EditUserFormInputReader;
import ua.foxminded.javaspring.lenskyi.university.service.RoleService;
import ua.foxminded.javaspring.lenskyi.university.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"", "/all"})
    public String getUserPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users-db-overview";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        EditUserFormInputReader inputReader = new EditUserFormInputReader();
        model.addAttribute("user", user);
        model.addAttribute("availableRoles", roleService.findAllRoles());
        model.addAttribute("inputReader", inputReader);
        model.addAttribute("pageTitle", "Change Role For User");

        return "forms/edit-user-form";
    }

    @PostMapping("/save")
    public String saveUser(EditUserFormInputReader inputReader, User user,
                           RedirectAttributes redirectAttributes, Model model) {
        try {
            User userToUpdate = userService.findById(user.getId());
            userService.addRole(userToUpdate, inputReader.getRoleNameToInclude());
            userService.removeRole(userToUpdate, inputReader.getRoleNameToExclude());
            model.addAttribute("users", userService.findAllUsers());
            redirectAttributes.addFlashAttribute("message", "The User has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/user/all";
    }
}