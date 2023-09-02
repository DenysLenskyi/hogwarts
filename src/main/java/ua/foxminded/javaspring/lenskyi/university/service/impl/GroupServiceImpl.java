package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public boolean existsByName(String groupName) {
        return groupRepository.existsByNameIgnoreCase(groupName);
    }

    @Transactional
    public void createNewGroupFromGroupDto(GroupDto groupDto) {
        if (!groupDto.getName().isEmpty() && !groupDto.getName().isBlank()) {
            Group group = new Group();
            group.setName(groupDto.getName());
            groupRepository.saveAndFlush(group);
        }
    }

    public boolean existsById(Long groupId) {
        return groupRepository.existsById(groupId);
    }

    @Transactional
    public void deleteById(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
