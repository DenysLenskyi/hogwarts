package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.List;

import static ua.foxminded.javaspring.lenskyi.university.controller.DefaultMessage.*;
import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
@RequestMapping("/group")
public class GroupController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String getGroupPage(Model model) {
        model.addAttribute("groups", groupService.findAll());
        log.info("getGroupPage called");
        return GROUP_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewGroupPage(Model model) {
        model.addAttribute("groupDto", new GroupDto());
        log.info("getNewGroupPage called");
        return CREATE_GROUP_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewGroup(@Valid GroupDto groupDto, Model model) {
        if (groupService.existsByName(groupDto.getName())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        groupService.createNewGroup(groupDto);
        log.info("Group created");
        model.addAttribute("message", CREATED_MESSAGE);
        model.addAttribute("groups", groupService.findAll());
        return GROUP_PAGE_TEMPLATE_NAME;
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteGroup(@PathVariable("groupId") Long id, Model model) {
        if (!groupService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        groupService.deleteById(id);
        log.info("Group deleted");
        model.addAttribute("message", DELETED_MESSAGE);
        model.addAttribute("groups", groupService.findAll());
        return GROUP_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("{groupId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getEditGroupPage(@PathVariable("groupId") Long id, Model model) {
        if (!groupService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        GroupDto currentGroup = groupService.findById(id);
        List<GroupDto> groups = groupService.findAll();
        groups.remove(currentGroup);
        log.info("getEditGroupPage called");
        model.addAttribute("groupsExcludeCurrent", groups);
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("groupDto", new GroupDto());
        return EDIT_GROUP_TEMPLATE_NAME;
    }

    @PutMapping("{groupId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editGroup(@PathVariable("groupId") Long id, @Valid GroupDto groupDto, Model model) {
        groupService.moveStudentsFromGroupToAnotherGroup(
                groupService.findGroupById(id), groupService.findByName(groupDto.getName())
        );
        log.info("Group edited");
        model.addAttribute("message", UPDATED_MESSAGE);
        model.addAttribute("groups", groupService.findAll());
        return GROUP_PAGE_TEMPLATE_NAME;
    }
}