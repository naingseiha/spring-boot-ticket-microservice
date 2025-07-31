package com.borntocodeme.user.service.impl;

import com.borntocodeme.user.constant.Constant;
import com.borntocodeme.user.dto.request.CreatePermissionRequestDTO;
import com.borntocodeme.user.dto.response.PermissionResponseDTO;
import com.borntocodeme.user.entity.Permission;
import com.borntocodeme.user.entity.Role;
import com.borntocodeme.user.repository.PermissionRepository;
import com.borntocodeme.user.repository.RoleRepository;
import com.borntocodeme.user.service.PermissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public PermissionResponseDTO create(CreatePermissionRequestDTO createPermissionRequestDTO) {
        if(permissionRepository.existsByName(createPermissionRequestDTO.getName())) {
            throw new IllegalArgumentException("Permission with name " + createPermissionRequestDTO.getName() + " already exists");
        }

        Permission permission = mapToPermission(createPermissionRequestDTO);
        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public PermissionResponseDTO update(Long id, CreatePermissionRequestDTO createPermissionRequestDTO) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission with id " + id + " does not exist"));

        if(!permission.getName().equals(createPermissionRequestDTO.getName()) && permissionRepository.existsByName(createPermissionRequestDTO.getName())) {
            throw new IllegalArgumentException("Permission with name " + createPermissionRequestDTO.getName() + " already exists");
        }

        permission.setName(createPermissionRequestDTO.getName());
        permission.setDescription(createPermissionRequestDTO.getDescription());
        permission.setStatus(createPermissionRequestDTO.getStatus());

        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public PermissionResponseDTO assignRoleToPermission(Long permissionId, Long roleId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission with id " + permissionId + " does not exist"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " does not exist"));

        permission.addRole(role);
        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public PermissionResponseDTO removeRoleFromPermission(Long permissionId, Long roleId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission with id " + permissionId + " does not exist"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " does not exist"));

       permission.removeRole(role);

        // Ensure the role is removed from the permission
        if (!permission.getRoles().contains(role)) {
            throw new IllegalArgumentException("Role with id " + roleId + " is not assigned to permission with id " + permissionId);
        }

        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public List<Permission> getPermissionsByNameIn(Set<String> names) {
        return permissionRepository.findAllByStatusAndNameIn(Constant.ACTIVE, names);
    }


    private Permission mapToPermission(CreatePermissionRequestDTO request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        permission.setStatus(request.getStatus());
        return permission;
    }

    private PermissionResponseDTO mapToPermissionResponse(Permission permission) {
        PermissionResponseDTO response = new PermissionResponseDTO();
        response.setId(permission.getId());
        response.setName(permission.getName());
        response.setDescription(permission.getDescription());
        response.setStatus(permission.getStatus());
        return response;
    }
}
