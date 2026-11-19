package com.ecommerce.user.service;

import com.ecommerce.user.dto.authentication.ChangePasswordDto;
import com.ecommerce.user.dto.authentication.LoginRequestDto;
import com.ecommerce.user.dto.authentication.LoginResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    LoginResponseDto login(LoginRequestDto dto);

    void logout();

    void changePassword(
            ChangePasswordDto dto,
            Authentication authentication
    );
}
