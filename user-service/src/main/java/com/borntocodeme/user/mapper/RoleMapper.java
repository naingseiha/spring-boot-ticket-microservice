package com.borntocodeme.user.mapper;

import com.borntocodeme.user.dto.request.CreateRoleRequestDTO;
import com.borntocodeme.user.dto.response.CreateRoleResponseDTO;
import com.borntocodeme.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toRole(CreateRoleRequestDTO createRoleRequestDTO);

    CreateRoleResponseDTO toCreateRoleResponseDTO(Role role);
}
