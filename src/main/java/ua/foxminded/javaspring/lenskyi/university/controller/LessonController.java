package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;

@Controller("/lesson")
public class LessonController {

    @Autowired
    LessonRepository lessonRepository;

    @RequestMapping("/lesson/db")
    public String home(Model model) {
        model.addAttribute("lessons", lessonRepository.findAll());
        return "lessons-db-overview";
    }
}