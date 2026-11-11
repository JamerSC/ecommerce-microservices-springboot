package com.ecommerce.user.service;

import com.ecommerce.user.dto.CreateUserDto;
import com.ecommerce.user.dto.UpdateUserDto;
import com.ecommerce.user.dto.UserDto;
import com.ecommerce.user.dto.UserListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public Page<UserListDto> getAllUsers(String search, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        return null;
    }

    @Override
    public List<UserListDto> getAllMockUsers() {
        return List.of();
    }

    @Override
    public UserDto getUserById(Long userId) {
        return null;
    }

    @Override
    public UserDto createUser(CreateUserDto userDto) {
        return null;
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
