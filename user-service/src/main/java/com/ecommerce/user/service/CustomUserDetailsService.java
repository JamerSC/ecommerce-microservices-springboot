package com.ecommerce.user.service;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom service responsible for retrieving user authentication
 * information from the database.
 *
 * Implements Spring Security's UserDetailsService interface
 * to integrate our custom User entity with Spring Security.
 */
@Service
@RequiredArgsConstructor
@NullMarked
public class CustomUserDetailsService implements UserDetailsService {

    // Repository responsible for retrieving user records from the database
    private final UserRepository userRepository;

    /**
     * Loads a user by username during authentication.
     *
     * @param username username provided during login
     * @return UserDetails containing username, password, and authorities
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Find the user in the database using their username.
        // If the user does not exist, throw an authentication-related exception.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + username
                ));

        // Create a collection of authorities (roles and permissions)
        // that will be assigned to the authenticated user.
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Retrieve all roles assigned to the user.
        user.getRoles().forEach(role -> {

            // Convert each role into a Spring Security authority.
            // The ROLE_ prefix is required by Spring Security's hasRole() convention.
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

            // Convert each permission into a Spring Security authority.
            // Permissions do not require the ROLE_ prefix.
            role.getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(permission.getName()))
            );
        });

        // Build Spring Security's built-in UserDetails implementation.
        // This object contains the user's username, encoded password,
        // and all assigned roles and permissions.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
