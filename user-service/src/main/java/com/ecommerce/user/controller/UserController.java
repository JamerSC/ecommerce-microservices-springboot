package com.ecommerce.user.controller;

import com.ecommerce.user.dto.user.UserDto;
import com.ecommerce.user.dto.user.UserListDto;
import com.ecommerce.user.dto.user.UserUpdateDto;
import com.ecommerce.user.service.UserService;
import com.ecommerce.user.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/ecommerce/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Page<UserListDto>>> getAllUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserListDto> users = userService.getAllUsers(search, dateFrom, dateTo, pageable);
        return ResponseEntity.ok(ApiResponse.<Page<UserListDto>>builder()
                .success(true)
                .message("Users fetched successfully.")
                .status(200)
                .timestamp(OffsetDateTime.now())
                .build());


//        @PreAuthorize("hasAuthority('CREATE_USERS')")
//        @PostMapping("/{employeeId}/create-user")
        @PostMapping("/")
        public ResponseEntity<ApiResponse<UserDto>> createUser(
                @Valid @RequestBody UserCreateDto dto) {
            UserDto createdUser = userService.createUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserDto>builder()
                    .success(true)
                    .message("User created successfully!")
                    .data(createdUser)
                    .status(HttpStatus.CREATED.value())
                    .timestamp(OffsetDateTime.now())
                    .build());
        }

//        @PreAuthorize("hasAuthority('UPDATE_USERS')")
        @PatchMapping("/{id}")
        public ResponseEntity<ApiResponse<UserDto>> updateUser(
                @PathVariable Long id, UserUpdateDto dto) {
            UserDto updatedUser = userService.updateUser(id, dto);
            return ResponseEntity.ok(ApiResponse.<UserDto>builder()
                    .success(true)
                    .message("User updated successfully!")
                    .data(updatedUser)
                    .status(HttpStatus.OK.value())
                    .timestamp(OffsetDateTime.now())
                    .build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.<Void>builder()
                    .success(true)
                    .message("User deleted successfully!")
                    .data(null)
                    .status(HttpStatus.OK.value())
                    .timestamp(OffsetDateTime.now())
                    .build());
        }
    }
}
