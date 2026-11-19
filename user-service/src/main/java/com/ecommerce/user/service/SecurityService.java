package com.ecommerce.user.service;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SecurityService {

    private final UserRepository userRepository;

    public User getUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        log.info("User details: {}", userDetails);
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("Authenticated user not found with username: {}", userDetails.getUsername());
                    return new RuntimeException("Authenticated user not found with username: " + userDetails.getUsername());
                });
    }
}
