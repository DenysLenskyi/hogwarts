package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.javaspring.lenskyi.university.repository.LessonStartEndTimeRepository;

@Controller("/lessonstartendtime")
public class LessonStartEndTimeController {

    @Autowired
    LessonStartEndTimeRepository lessonStartEndTimeRepository;

    @RequestMapping("/lessonstartendtime/db")
    public String home(Model model) {
        model.addAttribute("lessonstartendtimes", lessonStartEndTimeRepository.findAll());
        return "lessonstartendtimes-db-overview";
    }
}