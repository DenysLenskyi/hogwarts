package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private LessonRepository lessonRepository;

    public LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @GetMapping("/all")
    public String getLessonPage(Model model) {
        model.addAttribute("lessons", lessonRepository.findAll());
        return "lessons-db-overview";
    }
}