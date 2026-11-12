package com.ecommerce.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {

    private Boolean isActive;

    private Boolean isAdmin;

    @NotNull(message = "Select user roles")
    private Set<Long> roleIds;
}
