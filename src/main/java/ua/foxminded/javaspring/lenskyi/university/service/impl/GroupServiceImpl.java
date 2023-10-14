package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.model.User;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private UserRepository userRepository;
    private GroupEntityGroupDtoMapper groupEntityGroupDtoMapper;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository,
                            GroupEntityGroupDtoMapper groupEntityGroupDtoMapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupEntityGroupDtoMapper = groupEntityGroupDtoMapper;
    }

    @Override
    public List<GroupDto> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(groupEntityGroupDtoMapper::groupEntityToGroupDto)
                .sorted(Comparator.comparing(GroupDto::getId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean existsByName(String groupName) {
        return groupRepository.existsByName(groupName);
    }

    @Override
    @Transactional
    public void createNewGroup(GroupDto groupDto) {
        groupRepository.saveAndFlush(groupEntityGroupDtoMapper.groupDtoToGroupEntity(groupDto));
    }

    @Override
    public boolean existsById(Long groupId) {
        return groupRepository.existsById(groupId);
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public GroupDto findById(Long id) {
        return groupEntityGroupDtoMapper.groupEntityToGroupDto(findGroupById(id));
    }

    @Override
    public Group findGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Group findByName(String groupName) {
        return groupRepository.findByName(groupName).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional
    public void moveStudentsFromGroupToAnotherGroup(Group groupFrom, Group groupTo) {
        List<User> usersGroupFrom = userRepository.findAllByGroupName(groupFrom.getName());
        usersGroupFrom.forEach(student -> student.setGroup(groupTo));
        groupRepository.saveAndFlush(groupFrom);
        groupRepository.saveAndFlush(groupTo);
    }
}