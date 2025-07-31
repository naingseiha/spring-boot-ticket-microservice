package com.borntocodeme.user.service.impl;

import com.borntocodeme.user.constant.Constant;
import com.borntocodeme.user.dto.request.CreateRoleRequestDTO;
import com.borntocodeme.user.dto.response.CreateRoleResponseDTO;
import com.borntocodeme.user.entity.Role;
import com.borntocodeme.user.exception.RoleValidationException;
import com.borntocodeme.user.mapper.RoleMapper;
import com.borntocodeme.user.repository.RoleRepository;
import com.borntocodeme.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public CreateRoleResponseDTO create(CreateRoleRequestDTO createRoleRequestDTO) {
        if(!StringUtils.hasText(createRoleRequestDTO.getName())) {
            throw new RoleValidationException("name", "Role name cannot be empty");
        }

        if(roleRepository.findByName(createRoleRequestDTO.getName()).isPresent()) {
            throw new RoleValidationException("name", "Role name already exists");
        }

        Role role = roleMapper.toRole(createRoleRequestDTO);
        role.setStatus(Constant.ACTIVE);
        roleRepository.save(role);

        return roleMapper.toCreateRoleResponseDTO(role);
    }

    @Override
    public CreateRoleResponseDTO update(Long id, CreateRoleRequestDTO createRoleRequestDTO) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleValidationException("id", "Role not found"));

        // Check if the new name conflicts with an existing roes (except the current one)
        if(!role.getName().equals(createRoleRequestDTO.getName()) && roleRepository.existsByName(createRoleRequestDTO.getName())) {
            throw new RoleValidationException("name", "Role name already exists");
        }

        // Update role properties
        role.setName(createRoleRequestDTO.getName());
        role.setDescription(createRoleRequestDTO.getDescription());
        role.setStatus(Constant.ACTIVE);
        roleRepository.save(role);

        return roleMapper.toCreateRoleResponseDTO(role);
    }

    @Override
    public CreateRoleResponseDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleValidationException("id", "Role not found"));

        return roleMapper.toCreateRoleResponseDTO(role);
    }

    @Override
    public CreateRoleResponseDTO findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleMapper::toCreateRoleResponseDTO)
                .orElse(null);
    }

    @Override
    public List<Role> findAllByNameIn(Set<String> roleNames) {
        return roleRepository.findAllByNameIn(roleNames);
    }

    @Override
    public List<CreateRoleResponseDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
        return convertToRoleResponseDTO(roles);
    }

    @Override
    public List<CreateRoleResponseDTO> findAllRoleActive(String status) {
        List<Role> roles = roleRepository.findAllByStatus(status);
        return convertToRoleResponseDTO(roles);
    }

    private List<CreateRoleResponseDTO> convertToRoleResponseDTO(List<Role> roles) {
        if(roles.isEmpty()) {
            return List.of();
        }

        return roles.stream()
                .map(roleMapper::toCreateRoleResponseDTO)
                .toList();
    }
}
