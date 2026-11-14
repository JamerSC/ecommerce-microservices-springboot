package com.ecommerce.user.controller;

import com.ecommerce.user.dto.authentication.LoginRequestDto;
import com.ecommerce.user.dto.authentication.LoginResponseDto;
import com.ecommerce.user.service.AuthenticationService;
import com.ecommerce.user.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/ecommerce/authentications")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticateUser(
            @Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto loginResponse = authenticationService.login(dto);
        ApiResponse<LoginResponseDto> response = ApiResponse.<LoginResponseDto>builder()
                .success(true)
                .message("Login successfully")
                .data(loginResponse)
                .status(HttpStatus.OK.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasAuthority('LOGOUT_USERS')")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logoutUser() {
        authenticationService.logout();
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("User logged out successfully!")
                .status(HttpStatus.OK.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}
