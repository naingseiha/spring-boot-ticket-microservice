package com.borntocodeme.user.service;

import com.borntocodeme.user.dto.request.CreateUserRequestDTO;
import com.borntocodeme.user.dto.response.AuthResponse;
import com.borntocodeme.user.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    AuthResponse create(CreateUserRequestDTO createUserRequestDTO);

    UserResponseDTO update(Long id, CreateUserRequestDTO createUserRequestDTO);

    UserResponseDTO getById(Long id);

    void delete(Long id);

    List<UserResponseDTO> getAllUsers();
}
