package com.ecommerce.user.service;

import com.ecommerce.user.dto.authentication.LoginRequestDto;
import com.ecommerce.user.dto.authentication.LoginResponseDto;

public interface AuthenticationService {

    LoginResponseDto login(LoginRequestDto dto);

    void logout();
}
