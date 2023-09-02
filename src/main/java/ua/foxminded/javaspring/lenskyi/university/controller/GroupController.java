package ua.foxminded.javaspring.lenskyi.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

@Controller
@RequestMapping("/group")
public class GroupController {

    // To do:
    // 1. feature for groups page - a button that shows list of users of current group with features:
    // 1.1 rebase user to another group
    // 1.2 delete user
    // 2. admin can delete group if it contains no users only
    // 3. if a group contains a users - show it

    private static final String REDIRECT_TO_GROUPS_PAGE = "redirect:/group/all";
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String getGroupPage(Model model) {
        model.addAttribute("groups", groupService.findAll());
        return "groups-db-overview";
    }

    @GetMapping("/create-group-page")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("groupDto", new GroupDto());
        return "forms/create-group-form";
    }

    @PostMapping
    public String createNewGroup(GroupDto groupDto) {
        if (groupService.existsByName(groupDto.getName())) {
            return "error/400";
        } else {
            groupService.createNewGroupFromGroupDto(groupDto);
            return REDIRECT_TO_GROUPS_PAGE;
        }
    }

    @DeleteMapping("/{groupId}")
    public String deleteGroup(@PathVariable("groupId") Long id) {
        if (!groupService.existsById(id)) {
            return "error/400";
        } else {
            groupService.deleteById(id);
            return REDIRECT_TO_GROUPS_PAGE;
        }
    }
}