package com.ecommerce.user.service;

import com.ecommerce.user.dto.user.UserCreateDto;
import com.ecommerce.user.dto.user.UserUpdateDto;
import com.ecommerce.user.dto.user.UserDto;
import com.ecommerce.user.dto.user.UserListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    Page<UserListDto> getAllUsers(
            String search,
            LocalDate dateFrom,
            LocalDate dateTo,
            Pageable pageable
    );

    List<UserListDto> getAllMockUsers();

    UserDto getUserById(Long id);

    UserDto createUser(
            UserCreateDto dto,
            Authentication authentication
    );

    UserDto updateUser(
            Long id,
            UserUpdateDto dto,
            Authentication authentication
    );

    void deleteUser(Long id);
}
