package com.borntocodeme.user.controller;

import com.borntocodeme.common.dto.ApiResponse;
import com.borntocodeme.user.dto.request.CreatePermissionRequestDTO;
import com.borntocodeme.user.dto.response.PermissionResponseDTO;
import com.borntocodeme.user.entity.Permission;
import com.borntocodeme.user.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

   @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> createPermission(@RequestBody CreatePermissionRequestDTO createPermissionRequestDTO) {
        PermissionResponseDTO response = permissionService.create(createPermissionRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> updatePermission(@PathVariable Long id, @RequestBody CreatePermissionRequestDTO createPermissionRequestDTO) {
        PermissionResponseDTO response = permissionService.update(id, createPermissionRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{permissionId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> assignRoleToPermission(@PathVariable Long permissionId, @PathVariable Long roleId) {
        PermissionResponseDTO response = permissionService.assignRoleToPermission(permissionId, roleId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{permissionId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> removeRoleFromPermission(@PathVariable Long permissionId, @PathVariable Long roleId) {
        PermissionResponseDTO response = permissionService.removeRoleFromPermission(permissionId, roleId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
