package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepo;

    @GetMapping("/all")
    public String getUserPage(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users-db-overview";
    }
}