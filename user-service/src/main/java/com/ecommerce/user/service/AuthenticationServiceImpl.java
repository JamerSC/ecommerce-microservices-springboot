package com.ecommerce.user.service;

import com.ecommerce.user.dto.authentication.LoginRequestDto;
import com.ecommerce.user.dto.authentication.LoginResponseDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        return null;
//        try {
//            // authenticate the user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            dto.getUsername(),
//                            dto.getPassword()
//                    )
//            );
//            // unlike session-based, we don't need to set the context here for later requests.
//            // we only need it for the token generation below.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // retrieve user details
//            User user = userRepository.findByUsername(dto.getUsername())
//                    .orElseThrow(() -> new RuntimeException("User not found with username: " + dto.getUsername()));
//
//            // map
//            LoginResponseDto responseDto = authMapper.entityToLoginResponseDto(user);
//
//            // Generate JWT token
//            String jwt = jwtTokenProvider.generateToken(authentication);
//            responseDto.setAccessToken(jwt);
//
//            log.info("Login response {}", responseDto);
//            return responseDto;
//
//        } catch (org.springframework.security.core.AuthenticationException ex) {
//            // Convert Spring's AuthenticationException to your custom one
//            log.error("Login exception error");
//            throw new RuntimeException("Invalid username or password");
//                    //com.jamersc.gomezlegal_api.exception.AuthenticationException("Invalid username or password");
//        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
