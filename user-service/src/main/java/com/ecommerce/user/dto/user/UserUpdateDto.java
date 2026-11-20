package com.ecommerce.user.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    private Boolean isActive = true;

    private Boolean isAdmin = false;

    @NotNull(message = "Select user roles")
    private Set<Long> roleIds;
}
