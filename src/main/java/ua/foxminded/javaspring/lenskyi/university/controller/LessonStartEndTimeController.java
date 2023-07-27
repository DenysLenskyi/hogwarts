package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;

@Controller
@RequestMapping("/lessonstartendtime")
public class LessonStartEndTimeController {

    @Autowired
    LessonStartEndTimeRepository lessonStartEndTimeRepository;

    @GetMapping("/all")
    public String getLessonStartEndTimePage(Model model) {
        model.addAttribute("lessonstartendtimes", lessonStartEndTimeRepository.findAll());
        return "lessonstartendtimes-db-overview";
    }
}