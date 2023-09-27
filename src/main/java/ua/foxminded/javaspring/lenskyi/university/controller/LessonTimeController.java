package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonTimeRepository;
import ua.foxminded.javaspring.lenskyi.university.service.LessonTimeService;

@Controller
@RequestMapping("/lesson-time")
public class LessonTimeController {

    private LessonTimeService lessonTimeService;

    public LessonTimeController(LessonTimeService lessonTimeService) {
        this.lessonTimeService = lessonTimeService;
    }

    @GetMapping("/all")
    public String getLessonTimePage(Model model) {
        model.addAttribute("lessontimes", lessonTimeService.findAllDto());
        return "lessontimes-db-overview";
    }
}