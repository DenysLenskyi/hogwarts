package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.lenskyi.university.model.Role;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.util.UserInput;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/all")
    public String getUserPage(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users-db-overview";
    }

    @GetMapping("/edit/{id}")
    @RolesAllowed("admin")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("availableRoles", roleRepository.findAll());
        model.addAttribute("newRoleName", new UserInput());
        model.addAttribute("pageTitle", "Create new User");

        return "forms/edit-user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute UserInput newRoleName, User user,
                           RedirectAttributes redirectAttributes, Model model) {
        try {
            Set<Role> newRoles = new HashSet<>();
            newRoles.add(roleRepository.findRoleByName(newRoleName.getTextInput()).get());
            user.setRoles(newRoles);
            userRepo.save(user);
            model.addAttribute("users", userRepo.findAll());
            redirectAttributes.addFlashAttribute("message", "The User has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/user/all";
    }
}