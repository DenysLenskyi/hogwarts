package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.foxminded.javaspring.lenskyi.university.exception.NotUniqueUsernameException;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.ERROR_400_TEMPLATE_NAME;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({NotUniqueUsernameException.class, MethodArgumentNotValidException.class})
    public String getErrorPage(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return ERROR_400_TEMPLATE_NAME;
    }
}