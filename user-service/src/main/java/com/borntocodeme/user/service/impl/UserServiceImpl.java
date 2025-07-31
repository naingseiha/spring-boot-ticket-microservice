package com.borntocodeme.user.service.impl;

import com.borntocodeme.user.constant.Constant;
import com.borntocodeme.user.dto.request.CreateUserRequestDTO;
import com.borntocodeme.user.dto.response.AuthResponse;
import com.borntocodeme.user.dto.response.UserResponseDTO;
import com.borntocodeme.user.entity.User;
import com.borntocodeme.user.exception.UserValidationException;
import com.borntocodeme.user.mapper.UserMapper;
import com.borntocodeme.user.repository.GroupRepository;
import com.borntocodeme.user.repository.RoleRepository;
import com.borntocodeme.user.repository.UserRepository;
import com.borntocodeme.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public AuthResponse create(CreateUserRequestDTO createUserRequestDTO) {
        if(!StringUtils.hasText(createUserRequestDTO.getUsername())) {
            throw new UserValidationException("username", "Username cannot be empty");
        }

        if(!StringUtils.hasText(createUserRequestDTO.getPassword())) {
            throw new UserValidationException("password", "Password cannot be empty");
        }

        if(userRepository.findByUsername(createUserRequestDTO.getUsername()).isPresent()) {
            throw new UserValidationException("username", "Username already exists");
        }

        User user = userMapper.toUser(createUserRequestDTO);
        user.setStatus(Constant.ACTIVE);

        // handle roles
        if(createUserRequestDTO.getRoles() == null || createUserRequestDTO.getRoles().isEmpty()) {
           roleRepository.findByName(Constant.USER).ifPresent(user::addRole);
        }else {
            roleRepository.findAllByNameIn(createUserRequestDTO.getRoles()).forEach(user::addRole);
        }

        // handle groups
        if(createUserRequestDTO.getGroups() != null && !createUserRequestDTO.getGroups().isEmpty()) {
            // List<Group> groups = groupRepository.findAllByNameIn(createUserRequestDTO.getGroups());
            // groups.forEach(user::addGroup);

            groupRepository.findAllByNameIn(createUserRequestDTO.getGroups()).forEach(user::addGroup);
        }

        userRepository.save(user);

        return new AuthResponse();
    }

    @Override
    public UserResponseDTO update(Long id, CreateUserRequestDTO createUserRequestDTO) {
        return null;
    }

    @Override
    public UserResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return List.of();
    }
}
