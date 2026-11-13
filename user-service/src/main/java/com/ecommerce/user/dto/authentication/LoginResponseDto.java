package com.ecommerce.user.dto.authentication;

import lombok.Data;

import java.util.Set;

@Data
public class LoginResponseDto {
    private String fullName;
    private String username;
    private String jobTitle;
    private Boolean isActive;
    private Boolean isAdmin;
    private Set<String> roles;
    private Set<String> permissions;
    private String accessToken;
    private String tokenType = "Bearer";
}