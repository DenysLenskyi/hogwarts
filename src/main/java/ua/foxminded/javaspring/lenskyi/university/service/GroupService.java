package ua.foxminded.javaspring.lenskyi.university.service;

import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;

import java.util.List;

public interface GroupService {

    List<Group> findAll();

    boolean existsByName(String groupName);

    void createNewGroupFromGroupDto(GroupDto groupDto);

    boolean existsById(Long groupId);

    void deleteById(Long groupId);

    long getNumStudentsInGroup(String groupName);

    Group findById(Long id);

    Group findByName(String groupName);

    void moveStudentsFromGroupToAnotherGroup(Group groupFrom, Group groupTo);

    List<GroupDto> getAllGroupDtoWithNumStudents();
}