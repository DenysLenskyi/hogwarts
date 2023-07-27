package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
}