package com.borntocodeme.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupRequestDTO {
    @NotBlank(message = "Group name must not be blank")
    @Size(min = 3, max = 50, message = "Group name must be between 3 and 50 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private Set<String> roles;

    private Set<String> permissions;

    private String status;
}
