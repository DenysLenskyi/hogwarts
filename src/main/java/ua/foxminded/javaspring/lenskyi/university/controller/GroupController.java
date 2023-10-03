package ua.foxminded.javaspring.lenskyi.university.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.List;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.*;

@Controller
@RequestMapping("/group")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String getGroupPage(Model model) {
        model.addAttribute("groups", groupService.findAll());
        return GROUP_PAGE_TEMPLATE_NAME;
    }

    @GetMapping("/creation-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getNewGroupPage(Model model) {
        model.addAttribute("groupDto", new GroupDto());
        return CREATE_GROUP_TEMPLATE_NAME;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewGroup(@Valid GroupDto groupDto) {
        if (groupService.existsByName(groupDto.getName())) {
            return ERROR_400_TEMPLATE_NAME;
        }
        groupService.createNewGroup(groupDto);
        return REDIRECT_GROUP_PAGE;
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteGroup(@PathVariable("groupId") Long id) {
        if (!groupService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        }
        groupService.deleteById(id);
        return REDIRECT_GROUP_PAGE;
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
        model.addAttribute("groupsExcludeCurrent", groups);
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("groupDto", new GroupDto());
        return EDIT_GROUP_TEMPLATE_NAME;
    }

    @PutMapping("{groupId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editGroup(@PathVariable("groupId") Long id, @Valid GroupDto groupDto) {
        groupService.moveStudentsFromGroupToAnotherGroup(
                groupService.findGroupById(id), groupService.findByName(groupDto.getName())
        );
        return REDIRECT_GROUP_PAGE;
    }
}