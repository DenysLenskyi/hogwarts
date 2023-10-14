package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/logged-user-info")
    public String loggedUserPage() {
        return "logged-user-info";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}