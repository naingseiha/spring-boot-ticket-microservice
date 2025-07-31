package com.borntocodeme.user.controller;

import com.borntocodeme.user.dto.request.CreateUserRequestDTO;
import com.borntocodeme.user.dto.response.AuthResponse;
import com.borntocodeme.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private ResponseEntity<AuthResponse> createUser(@RequestBody CreateUserRequestDTO request) {
        return ResponseEntity.ok(userService.create(request));
    }
}
