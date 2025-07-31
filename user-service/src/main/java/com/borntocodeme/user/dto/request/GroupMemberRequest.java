package com.borntocodeme.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GroupMemberRequest {
    @NotNull(message = "Group ID must not be null")
    private List<Long> userIds;
}
