package com.borntocodeme.user.controller;

import com.borntocodeme.common.dto.ApiResponse;
import com.borntocodeme.user.dto.request.CreateGroupRequestDTO;
import com.borntocodeme.user.dto.request.GroupMemberRequest;
import com.borntocodeme.user.dto.response.GroupResponseDTO;
import com.borntocodeme.user.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GroupResponseDTO>> createGroup(@Valid @RequestBody CreateGroupRequestDTO createGroupRequestDTO) {

        return ResponseEntity.ok(ApiResponse.success(groupService.create(createGroupRequestDTO)));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponseDTO>> updateGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody CreateGroupRequestDTO createGroupRequestDTO) {
        return ResponseEntity.ok(ApiResponse.success(groupService.update(groupId, createGroupRequestDTO)));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<GroupResponseDTO>> addMemberToGroup(@PathVariable Long groupId, @Valid @RequestBody GroupMemberRequest groupMemberRequest) {
        GroupResponseDTO group = groupService.addMemberToGroup(groupId, groupMemberRequest);

        return ResponseEntity.ok(ApiResponse.success(group));
    }

    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<GroupResponseDTO>> removeMemberFromGroup(@PathVariable Long groupId, @Valid @RequestBody GroupMemberRequest groupMemberRequest) {
        GroupResponseDTO group = groupService.removeMemberFromGroup(groupId, groupMemberRequest);

        return ResponseEntity.ok(ApiResponse.success(group));
    }

}
