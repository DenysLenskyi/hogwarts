package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.controller.form.reader.EditUserFormInputReader;

import java.util.Optional;

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
    public String editUser(@PathVariable("id") Long id, Model model) throws ChangeSetPersister.NotFoundException {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        User user = optional.get();
        EditUserFormInputReader inputReader = new EditUserFormInputReader();
        model.addAttribute("user", user);
        model.addAttribute("availableRoles", roleRepository.findAll());
        model.addAttribute("inputReader", inputReader);
        model.addAttribute("pageTitle", "Change Role For User");

        return "forms/edit-user-form";
    }

    @PostMapping("/save-edited-user")
    public String saveUser(EditUserFormInputReader inputReader, User user,
                           RedirectAttributes redirectAttributes, Model model) {
        try {
            User userToUpdate = userRepo.findById(user.getId()).orElseThrow();
            userToUpdate.getRoles().add(roleRepository.findRoleByName(inputReader.getRoleNameToInclude()).orElseThrow());
            userToUpdate.getRoles().remove(roleRepository.findRoleByName(inputReader.getRoleNameToExclude()).orElseThrow());
            userRepo.save(userToUpdate);
            model.addAttribute("users", userRepo.findAll());
            redirectAttributes.addFlashAttribute("message", "The User has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/user/all";
    }
}