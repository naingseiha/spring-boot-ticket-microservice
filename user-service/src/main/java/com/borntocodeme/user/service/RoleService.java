package com.borntocodeme.user.service;

import com.borntocodeme.user.dto.request.CreateRoleRequestDTO;
import com.borntocodeme.user.dto.request.CreateUserRequestDTO;
import com.borntocodeme.user.dto.response.CreateRoleResponseDTO;
import com.borntocodeme.user.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    CreateRoleResponseDTO create (CreateRoleRequestDTO createRoleRequestDTO);

    CreateRoleResponseDTO update(Long id, CreateRoleRequestDTO createRoleRequestDTO);

    CreateRoleResponseDTO findById(Long id);

    CreateRoleResponseDTO findByName(String name);

    List<Role> findAllByNameIn(Set<String> roleNames);

    List<CreateRoleResponseDTO> findAll();

    List<CreateRoleResponseDTO> findAllRoleActive(String status);
}
