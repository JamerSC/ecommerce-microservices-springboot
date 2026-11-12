package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserCreateDto;
import com.ecommerce.user.dto.UserUpdateDto;
import com.ecommerce.user.dto.UserDto;
import com.ecommerce.user.dto.UserListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    UserDto createUser(UserCreateDto userDto);

    UserDto updateUser(Long id, UserUpdateDto userDto);

    void deleteUser(Long id);
}
