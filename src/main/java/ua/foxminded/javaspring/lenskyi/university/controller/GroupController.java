package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;

@Controller("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @RequestMapping("/group/db")
    public String home(Model model) {
        model.addAttribute("groups", groupRepository.findAll());
        return "groups-db-overview";
    }
}