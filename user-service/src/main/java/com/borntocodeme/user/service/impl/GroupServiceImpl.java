package com.borntocodeme.user.service.impl;

import com.borntocodeme.user.constant.Constant;
import com.borntocodeme.user.dto.request.CreateGroupRequestDTO;
import com.borntocodeme.user.dto.request.GroupMemberRequest;
import com.borntocodeme.user.dto.response.GroupResponseDTO;
import com.borntocodeme.user.entity.Group;
import com.borntocodeme.user.entity.Permission;
import com.borntocodeme.user.entity.Role;
import com.borntocodeme.user.entity.User;
import com.borntocodeme.user.repository.GroupRepository;
import com.borntocodeme.user.repository.UserRepository;
import com.borntocodeme.user.service.GroupService;
import com.borntocodeme.user.service.PermissionService;
import com.borntocodeme.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, RoleService roleService, PermissionService permissionService, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.userRepository = userRepository;
    }

    @Override
    public GroupResponseDTO create(CreateGroupRequestDTO createGroupRequestDTO) {
        if(groupRepository.existsByName(createGroupRequestDTO.getName())) {
            throw new RuntimeException("Group with name " + createGroupRequestDTO.getName() + " already exists");
        }

        Set<Role> roles = createGroupRequestDTO.getRoles() != null
                ? new HashSet<>(roleService.findAllByNameIn(createGroupRequestDTO.getRoles()))
                : Set.of();

        Set<Permission> permissions = createGroupRequestDTO.getPermissions() != null
                ? new java.util.HashSet<>(permissionService.getPermissionsByNameIn(createGroupRequestDTO.getPermissions()))
                : Set.of();

        Group group = mapToGroup(createGroupRequestDTO);
        group.setRoles(roles);
        group.setPermissions(permissions);

        groupRepository.save(group);

        return mapToDto(group);
    }

    @Override
    public GroupResponseDTO update(Long id, CreateGroupRequestDTO createGroupRequestDTO) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group with id " + id + " does not exist"));

        if(createGroupRequestDTO.getName() != null && !createGroupRequestDTO.getName().equals(group.getName())) {
            if(groupRepository.existsByName(createGroupRequestDTO.getName())) {
                throw new RuntimeException("Group with name " + createGroupRequestDTO.getName() + " already exists");
            }
            group.setName(createGroupRequestDTO.getName());
        }

        if(createGroupRequestDTO.getDescription() != null) {
            group.setDescription(createGroupRequestDTO.getDescription());
        }

        if(createGroupRequestDTO.getRoles() != null) {
            Set<Role> roles = new HashSet<>(roleService.findAllByNameIn(createGroupRequestDTO.getRoles()));
            group.setRoles(roles);
        }

        if(createGroupRequestDTO.getPermissions() != null) {
            Set<Permission> permissions = new HashSet<>(permissionService.getPermissionsByNameIn(createGroupRequestDTO.getPermissions()));
            group.setPermissions(permissions);
        }

        if(StringUtils.hasText(createGroupRequestDTO.getStatus())) {
            group.setStatus(createGroupRequestDTO.getStatus());
        }

        groupRepository.save(group);

        return mapToDto(group);
    }

    @Override
    public GroupResponseDTO addMemberToGroup(Long groupId, GroupMemberRequest groupMemberRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " does not exist"));

        List<User> users = userRepository.findAllById(groupMemberRequest.getUserIds());

        for(User user : users) {
            if(!group.getUsers().contains(user)) {
                group.addUser(user);
            } else {
                log.warn("User with id {} is already a member of group with id {}", user.getId(), groupId);
            }
        }

        groupRepository.save(group);

        return mapToDto(group);
    }

    @Override
    public GroupResponseDTO removeMemberFromGroup(Long groupId, GroupMemberRequest groupMemberRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " does not exist"));

        List<User> users = userRepository.findAllById(groupMemberRequest.getUserIds());

        for(User user : users) {
            if(group.getUsers().contains(user)) {
                group.removeUser(user);
            } else {
                log.warn("User with id {} is not a member of group with id {}", user.getId(), groupId);
            }
        }

        groupRepository.save(group);

        return mapToDto(group);
    }

    private GroupResponseDTO mapToDto(Group group) {
        return GroupResponseDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .roles(group.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toSet()))
                .permissions(group.getPermissions().stream().map(Permission::getName).collect(java.util.stream.Collectors.toSet()))
                .status(group.getStatus())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .createdBy(group.getCreatedBy())
                .updatedBy(group.getUpdatedBy())
                .memberCount(group.getUsers().size())
                .build();
    }

    private Group mapToGroup(CreateGroupRequestDTO request) {
        Group group = new Group();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setStatus(Constant.ACTIVE);

        return group;
    }

}
