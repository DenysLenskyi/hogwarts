package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

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

    @GetMapping("/create-classroom-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String showCreateNewClassroomForm(Model model) {
        model.addAttribute("classroomDto", new ClassroomDto());
        return CREATE_CLASSROOM_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewClassroom(ClassroomDto classroomDto) {
        if (classroomService.existsByName(classroomDto.getName())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        classroomService.createNewClassroomFromClassroomDto(classroomDto);
        return REDIRECT_CLASSROOM_PAGE;
    }

    @DeleteMapping("/{classroomId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteClassroom(@PathVariable("classroomId") Long id) {
        if (!classroomService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        classroomService.deleteClassroomById(id);
        return REDIRECT_CLASSROOM_PAGE;
    }

    @GetMapping("/{classroomId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin', 'professor')")
    public String showEditClassroomForm(@PathVariable("classroomId") Long id, Model model) {
        if (!classroomService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        ClassroomDto classroomDto = classroomService.findById(id);
        model.addAttribute("classroomDto", classroomDto);
        return EDIT_CLASSROOM_TEMPLATE_NAME;
    }

    @PutMapping("/{classroomId}")
    @PreAuthorize("hasAnyAuthority('admin', 'professor')")
    public String editClassroom(@PathVariable("classroomId") Long id, ClassroomDto classroomDto) {
        if (classroomService.existsByName(classroomDto.getName()) && !classroomDto.getName()
                .equals(classroomService.findById(id).getName())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        classroomDto.setId(id);
        classroomService.updateClassroomFromClassroomDto(classroomDto);
        return REDIRECT_CLASSROOM_PAGE;
    }
}