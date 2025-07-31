package com.borntocodeme.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String tokenType;
    @JsonProperty("token_type")
    private String username;
    private String role;
}
