package com.borntocodeme.user.service;

import com.borntocodeme.user.dto.request.CreatePermissionRequestDTO;
import com.borntocodeme.user.dto.response.PermissionResponseDTO;
import com.borntocodeme.user.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PermissionService {
    PermissionResponseDTO create(CreatePermissionRequestDTO createPermissionRequestDTO);

    PermissionResponseDTO update(Long id, CreatePermissionRequestDTO createPermissionRequestDTO);

    PermissionResponseDTO assignRoleToPermission(Long permissionId, Long roleId);

    PermissionResponseDTO removeRoleFromPermission(Long permissionId, Long roleId);

    List<Permission> getPermissionsByNameIn(Set<String> names);

}
