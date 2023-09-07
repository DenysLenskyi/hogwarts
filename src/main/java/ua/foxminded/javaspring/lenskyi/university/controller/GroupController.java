package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.List;

import static ua.foxminded.javaspring.lenskyi.university.util.Constants.EDIT_GROUP_TEMPLATE_NAME;
import static ua.foxminded.javaspring.lenskyi.university.util.Constants.ERROR_400_TEMPLATE_NAME;

@Controller
@RequestMapping("/group")
public class GroupController {

    private static final String REDIRECT_TO_GROUPS_PAGE = "redirect:/group/all";
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String getGroupPage(Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("groupService", groupService);
        return "groups-db-overview";
    }

    @GetMapping("/create-group-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("groupDto", new GroupDto());
        return "forms/create-group-form";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createNewGroup(GroupDto groupDto) {
        if (groupService.existsByName(groupDto.getName())) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            groupService.createNewGroupFromGroupDto(groupDto);
            return REDIRECT_TO_GROUPS_PAGE;
        }
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteGroup(@PathVariable("groupId") Long id) {
        if (!groupService.existsById(id)) {
            return "error/400";
        } else {
            groupService.deleteById(id);
            return REDIRECT_TO_GROUPS_PAGE;
        }
    }

    @GetMapping("{groupId}/edit-page")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String showEditGroupForm(@PathVariable("groupId") Long id, Model model) {
        if (!groupService.existsById(id)) {
            return ERROR_400_TEMPLATE_NAME;
        } else {
            Group currentGroup = groupService.findById(id);
            List<Group> groups = groupService.findAll();
            groups.remove(currentGroup);
            model.addAttribute("groupsExcludeCurrent", groups);
            model.addAttribute("currentGroup", currentGroup);
            model.addAttribute("groupDto", new GroupDto());
            return EDIT_GROUP_TEMPLATE_NAME;
        }
    }

    @PutMapping("{groupId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String editGroup(@PathVariable("groupId") Long id, GroupDto groupDto) {
        groupService.moveStudentsFromGroupToAnotherGroup(
                groupService.findById(id), groupService.findByName(groupDto.getName())
        );
        return REDIRECT_TO_GROUPS_PAGE;
    }
}