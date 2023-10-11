package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.ClassroomDto;
import ua.foxminded.javaspring.lenskyi.university.service.ClassroomService;

import static ua.foxminded.javaspring.lenskyi.university.controller.DefaultMessage.*;
import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping("/all")
    public String getClassroomPage(Model model) {
        model.addAttribute("classrooms", classroomService.findAll());
        log.info("getClassroomPage called");
        return CLASSROOM_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewClassroomPage(Model model) {
        model.addAttribute("classroomDto", new ClassroomDto());
        log.info("getNewClassroomPage called");
        return CREATE_CLASSROOM_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewClassroom(@Valid ClassroomDto classroomDto, Model model) {
        if (classroomService.existsByName(classroomDto.getName())) {
            log.warn("Attempt to create the classroom with existed name");
            return ERROR_400_TEMPLATE_NAME;
        }
        classroomService.createClassroom(classroomDto);
        log.info("New classroom created");
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("message", CREATED_MESSAGE);
        return CLASSROOM_PAGE_TEMPLATE_NAME;
    }

    @DeleteMapping("/{classroomId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteClassroom(@PathVariable("classroomId") Long id, Model model) {
        if (!classroomService.existsById(id)) {
            log.warn("Attempt to delete a classroom with the id which doesn't exist");
            return ERROR_400_TEMPLATE_NAME;
        }
        classroomService.deleteClassroomById(id);
        log.info("Classroom deleted");
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("message", DELETED_MESSAGE);
        return CLASSROOM_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/{classroomId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin', 'professor')")
    public String getEditClassroomPage(@PathVariable("classroomId") Long id, Model model) {
        if (!classroomService.existsById(id)) {
            log.warn("Attempt to edit a classroom with the id which doesn't exist");
            return ERROR_400_TEMPLATE_NAME;
        }
        ClassroomDto classroomDto = classroomService.findById(id);
        model.addAttribute("classroomDto", classroomDto);
        log.info("getEditClassroomPage called");
        return EDIT_CLASSROOM_TEMPLATE_NAME;
    }

    @PutMapping("/{classroomId}")
    @PreAuthorize("hasAnyAuthority('admin', 'professor')")
    public String editClassroom(@PathVariable("classroomId") Long id, @Valid ClassroomDto classroomDto,
                                Model model) {
        if (classroomService.existsByName(classroomDto.getName()) && !classroomDto.getName()
                .equals(classroomService.findById(id).getName())) {
            log.warn("Attempt to save the classroom after editing with not unique name");
            return ERROR_400_TEMPLATE_NAME;
        }
        classroomDto.setId(id);
        classroomService.updateClassroom(classroomDto);
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("message", UPDATED_MESSAGE);
        log.info("Classroom edited");
        return CLASSROOM_PAGE_TEMPLATE_NAME;
    }
}