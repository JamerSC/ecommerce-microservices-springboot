package com.ecommerce.user.service;

import com.ecommerce.user.dto.user.*;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.ResourceNotFoundException;
import com.ecommerce.user.repository.RoleRepository;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.repository.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    @Override
    public Page<UserListDto> getAllUsers(
            String search,
            LocalDate dateFrom,
            LocalDate dateTo,
            Pageable pageable
    ) {

        log.debug("Fetching users with search: {}, dateFrom: {}, dateTo: {}, pageable: {}",
                search, dateFrom, dateTo, pageable);

        Specification<User> spec = Specification.allOf(
                UserSpecification.search(search),
                UserSpecification.dateBetween(dateFrom, dateTo)
        );

        return userRepository.findAll(spec, pageable)
                .map(user -> new UserListDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getIsActive(),
                        user.getIsAdmin(),
                        user.getCreatedAt()
                ));
    }

    @Override
    public List<UserListDto> getAllMockUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserListDto.builder()
                        .id(user.getId())
                        .fullName(user.getFirstName() + " " + user.getLastName())
                        .email(user.getEmail())
                        .isActive(user.getIsActive())
                        .isAdmin(user.getIsAdmin())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));

        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getIsActive(),
                user.getIsAdmin(),
                user.getRoles().stream()
                        .map(role -> new RoleDto(
                                role.getId(),
                                role.getName()
                        ))
                        .toList(),
                user.getCreatedAt(),
                user.getCreatedBy() != null ? user.getCreatedBy().getId() : null,
                user.getUpdatedAt(),
                user.getUpdatedBy() != null ? user.getUpdatedBy().getId() : null
        );
    }

    @Override
    public UserDto createUser(UserCreateDto dto, Authentication authentication) {
        User currentUser =
                securityService.getUserAuthentication(authentication);

        // Validate password confirmation
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException(
                    "Password and confirm password do not match"
            );
        }

        // Check duplicate email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        // Check duplicate username
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException(
                    "Username is already taken"
            );
        }

        // Retrieve roles
        Set<Role> roles = getRolesByIds(dto.getRoleIds());

        // Create user
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        // Hash password before saving
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setIsActive(
                dto.getIsActive() == null || dto.getIsActive()
        );

        user.setIsAdmin(
                dto.getIsAdmin() != null ? dto.getIsAdmin() : false
        );

        user.setRoles(roles);

        // Audit information
        user.setCreatedBy(currentUser);
        user.setUpdatedBy(currentUser);

        User savedUser = userRepository.save(user);

        log.info("User created successfully. ID: {}", savedUser.getId());

        return mapToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDto dto, Authentication authentication) {

        User currentUser =
                securityService.getUserAuthentication(authentication);

        // Find existing user
        User user = findUserById(id);

        // Update account status
        if (dto.getIsActive() != null) {
            user.setIsActive(dto.getIsActive());
        }

        // Update admin status
        if (dto.getIsAdmin() != null) {
            user.setIsAdmin(dto.getIsAdmin());
        }

        // Update user roles
        if (dto.getRoleIds() != null) {
            Set<Role> roles = getRolesByIds(dto.getRoleIds());
            user.setRoles(roles);
        }

        // Update audit information
        user.setUpdatedBy(currentUser);

        User updatedUser = userRepository.save(user);

        log.info("User updated successfully. ID: {}", updatedUser.getId());

        return mapToUserDto(updatedUser);
    }


    // FIND USER
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));
    }

    // FIND ROLES
    private Set<Role> getRolesByIds(Set<Long> roleIds) {

        if (roleIds == null || roleIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "Select at least one user role"
            );
        }

        List<Role> roles = roleRepository.findAllById(roleIds);

        if (roles.size() != roleIds.size()) {
            throw new ResourceNotFoundException(
                    "One or more selected roles do not exist"
            );
        }

        return new HashSet<>(roles);
    }

    // MAP USER ENTITY TO DTO
    private UserDto mapToUserDto(User user) {

        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getIsActive(),
                user.getIsAdmin(),
                user.getRoles().stream()
                        .map(role -> new RoleDto(
                                role.getId(),
                                role.getName()
                        ))
                        .toList(),
                user.getCreatedAt(),
                user.getCreatedBy() != null
                        ? user.getCreatedBy().getId() : null,
                user.getUpdatedAt(),
                user.getUpdatedBy() != null
                        ? user.getUpdatedBy().getId() : null
        );
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    private User getUser(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        assert userDetails != null;
//        return userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> {
//                    log.error("Authenticated user not found with username: {}", userDetails.getUsername());
//                    return new ResourceNotFoundException("Authenticated user not found with username: " + userDetails.getUsername());
//                });
//
//    }

}
