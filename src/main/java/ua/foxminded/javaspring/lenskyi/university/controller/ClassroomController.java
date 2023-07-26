package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.ClassroomRepository;

@Controller("/classroom")
public class ClassroomController {
    @Autowired
    ClassroomRepository classroomRepository;

    @RequestMapping("/classroom/db")
    public String home(Model model) {
        model.addAttribute("classrooms", classroomRepository.findAll());
        return "classrooms-db-overview";
    }
}