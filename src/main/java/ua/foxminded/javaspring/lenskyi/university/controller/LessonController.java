package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonRepository;
import ua.foxminded.javaspring.lenskyi.university.service.LessonService;

@Controller
@RequestMapping("/lesson")
public class LessonController {

   private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/all")
    public String getLessonPage(Model model) {
        model.addAttribute("lessons", lessonService.findAllLessonDto());
        return "lessons-page";
    }
}