package com.borntocodeme.user.service;

import com.borntocodeme.user.dto.request.CreateGroupRequestDTO;
import com.borntocodeme.user.dto.request.GroupMemberRequest;
import com.borntocodeme.user.dto.response.GroupResponseDTO;

public interface GroupService {
    GroupResponseDTO create(CreateGroupRequestDTO createGroupRequestDTO);

    GroupResponseDTO update(Long id, CreateGroupRequestDTO createGroupRequestDTO);

    GroupResponseDTO addMemberToGroup(Long groupId, GroupMemberRequest groupMemberRequest);

    GroupResponseDTO removeMemberFromGroup(Long groupId, GroupMemberRequest groupMemberRequest);
}
