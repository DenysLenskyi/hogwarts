package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    String login() {
        return LOGIN_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/login/readme")
    public String readme() {
        return README_PAGE_TEMPLATE_NAME;
    }
}