package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.RoleRepository;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/all")
    public String getRolePage(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "roles-db-overview";
    }
}