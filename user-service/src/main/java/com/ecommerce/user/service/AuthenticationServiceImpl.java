package com.ecommerce.user.service;

import com.ecommerce.user.dto.authentication.LoginRequestDto;
import com.ecommerce.user.dto.authentication.LoginResponseDto;
import com.ecommerce.user.entity.Permission;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
//    private final AuthMapper authMapper;
//    private final SecurityService securityService;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        log.info("Logging in user with email: {}", dto.getUsername());
//        return null;
        try {
            // authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
            // unlike session-based, we don't need to set the context here for later requests.
            // we only need it for the token generation below.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // retrieve user details
            User user = userRepository.findByUsername(dto.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + dto.getUsername()));

            // map
            LoginResponseDto responseDto = LoginResponseDto.builder()
                    .fullName(user.getFullName())
                    .username(user.getUsername())
//                    .jobTitle(user.getJobTitle())
                    .isActive(user.getIsActive())
                    .isAdmin(user.getIsAdmin())
                    .roles(user.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toSet()))
                    .permissions(user.getRoles().stream()
                            .flatMap(role -> role.getPermissions().stream())
                            .map(Permission::getName)
                            .collect(java.util.stream.Collectors.toSet()))
                    .build();

            // Generate JWT token
            String jwt = jwtTokenProvider.generateToken(authentication);
            responseDto.setAccessToken(jwt);

            log.info("Login response {}", responseDto);
            return responseDto;

        } catch (AuthenticationException ex) {

            log.warn("Authentication failed for: {}", dto.getUsername());

            log.error(
                    "Authentication failed. Username: {}, Exception: {}, Message: {}",
                    dto.getUsername(),
                    ex.getClass().getSimpleName(),
                    ex.getMessage()
            );
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
