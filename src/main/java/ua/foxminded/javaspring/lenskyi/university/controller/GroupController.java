package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @GetMapping("/all")
    public String getGroupPage(Model model) {
        model.addAttribute("groups", groupRepository.findAll());
        return "groups-db-overview";
    }
}