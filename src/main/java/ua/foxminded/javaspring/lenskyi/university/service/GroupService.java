package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;

import java.util.List;

public interface GroupService {

    List<GroupDto> findAll();

    boolean existsByName(String groupName);

    void createNewGroup(GroupDto groupDto);

    boolean existsById(Long groupId);

    void deleteById(Long groupId);

    GroupDto findById(Long id);

    Group findGroupById(Long id);

    Group findByName(String groupName);

    void moveStudentsFromGroupToAnotherGroup(Group groupFrom, Group groupTo);
}