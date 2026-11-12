package com.ecommerce.user.service;

import com.ecommerce.user.dto.*;
import com.ecommerce.user.entity.User;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
                .orElseThrow(() -> new RuntimeException(
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
    public UserDto createUser(UserCreateDto userDto) {

        return null;
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User getUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("Authenticated user not found with username: {}", userDetails.getUsername());
                    return new RuntimeException("Authenticated user not found with username: " + userDetails.getUsername());
                });
    }
}
