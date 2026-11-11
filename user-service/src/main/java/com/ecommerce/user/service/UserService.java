package com.ecommerce.user.service;

import com.ecommerce.user.dto.CreateUserDto;
import com.ecommerce.user.dto.UpdateUserDto;
import com.ecommerce.user.dto.UserDto;
import com.ecommerce.user.dto.UserListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

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

    UserDto getUserById(Long userId);

    UserDto createUser(CreateUserDto userDto);

    UserDto updateUser(Long userId, UpdateUserDto userDto);

    void deleteUser(Long userId);
}
