package com.borntocodeme.user.dto.request;

import com.borntocodeme.user.entity.Group;
import com.borntocodeme.user.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Set;

@Data
public class CreatePermissionRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("roles")
    private Set<Role> roles;

    @JsonProperty("groups")
    private Set<Group> groups;
}
