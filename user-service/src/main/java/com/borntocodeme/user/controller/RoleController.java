package com.borntocodeme.user.controller;

import com.borntocodeme.user.dto.request.CreateRoleRequestDTO;
import com.borntocodeme.user.dto.response.CreateRoleResponseDTO;
import com.borntocodeme.user.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController{
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<CreateRoleResponseDTO> createRole(@RequestBody CreateRoleRequestDTO createRoleRequestDTO) {
        CreateRoleResponseDTO response = roleService.create(createRoleRequestDTO);
        return ResponseEntity.ok(response);
    }
}
