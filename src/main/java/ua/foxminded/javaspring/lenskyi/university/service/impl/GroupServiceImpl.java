package ua.foxminded.javaspring.lenskyi.university.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.GroupDto;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper.GroupEntityGroupDtoMapper;
import ua.foxminded.javaspring.lenskyi.university.model.Group;
import ua.foxminded.javaspring.lenskyi.university.repository.GroupRepository;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;
import ua.foxminded.javaspring.lenskyi.university.service.GroupService;

import java.util.List;

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

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public boolean existsByName(String groupName) {
        return groupRepository.existsByNameIgnoreCase(groupName);
    }

    @Transactional
    public void createNewGroupFromGroupDto(GroupDto groupDto) {
        if (!groupDto.getName().isEmpty() && !groupDto.getName().isBlank()) {
            groupRepository.saveAndFlush(groupEntityGroupDtoMapper.groupDtoToGroupEntity(groupDto));
        }
    }

    public boolean existsById(Long groupId) {
        return groupRepository.existsById(groupId);
    }

    @Transactional
    public void deleteById(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public long getNumStudentsInGroup(String groupName) {
        return userRepository.countAllByGroupName(groupName);
    }
}
