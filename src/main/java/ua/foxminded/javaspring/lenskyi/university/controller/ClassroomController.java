package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    private ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping("/all")
    public String getClassroomPage(Model model) {
        model.addAttribute("classrooms", classroomService.findAll());
        return "classrooms-db-overview";
    }
}