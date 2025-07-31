package com.borntocodeme.user.mapper;

import com.borntocodeme.user.dto.request.CreateUserRequestDTO;
import com.borntocodeme.user.dto.response.AuthResponse;
import com.borntocodeme.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "groups", ignore = true)
    User toUser(CreateUserRequestDTO createUserRequestDTO);

    AuthResponse toAuthResponse(User user);

}
