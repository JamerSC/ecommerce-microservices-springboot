package com.ecommerce.user.service;

import com.ecommerce.user.dto.user.UserCreateDto;
import com.ecommerce.user.dto.user.UserDto;
import com.ecommerce.user.dto.user.UserUpdateDto;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.ResourceNotFoundException;
import com.ecommerce.user.repository.RoleRepository;
import com.ecommerce.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityService securityService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private User currentUser;
    private Role role;

    @BeforeEach
    void setUp() {

        currentUser = new User();
        currentUser.setId(1L);
        currentUser.setFirstName("Admin");
        currentUser.setLastName("User");
        currentUser.setEmail("admin@mail.com");
        currentUser.setUsername("admin");

        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
    }

    // ==========================================
    // GET USER BY ID
    // ==========================================
//
//    @Test
//    void getUserById_ShouldReturnUser_WhenUserExists() {
//
//        // Arrange
//        User user = new User();
//        user.setId(2L);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setEmail("john@mail.com");
//        user.setUsername("johndoe");
//        user.setIsActive(true);
//        user.setIsAdmin(false);
//        user.setRoles(Set.of(role));
//
//        when(userRepository.findById(2L))
//                .thenReturn(Optional.of(user));
//
//        // Act
//        UserDto result = userService.getUserById(2L);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2L, result.getId());
//        assertEquals("john@mail.com", result.getEmail());
//        assertEquals("johndoe", result.username());
//
//        verify(userRepository).findById(2L);
//    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {

        // Arrange
        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserById(99L)
        );

        verify(userRepository).findById(99L);
    }

    // ==========================================
    // CREATE USER
    // ==========================================

//    @Test
//    void createUser_ShouldCreateUser_WhenDataIsValid() {
//
//        // Arrange
//        UserCreateDto dto = new UserCreateDto();
//
//        dto.setFirstName("John");
//        dto.setMiddleName("Michael");
//        dto.setLastName("Doe");
//        dto.setEmail("john@mail.com");
//        dto.setUsername("johndoe");
//        dto.setPassword("Test123$");
//        dto.setConfirmPassword("Test123$");
//        dto.setIsActive(true);
//        dto.setIsAdmin(false);
//        dto.setRoleIds(Set.of(1L));
//
//        when(securityService.getUserAuthentication(authentication))
//                .thenReturn(currentUser);
//
//        when(userRepository.existsByEmail(dto.getEmail()))
//                .thenReturn(false);
//
//        when(userRepository.existsByUsername(dto.getUsername()))
//                .thenReturn(false);
//
//        when(roleRepository.findAllById(dto.getRoleIds()))
//                .thenReturn(List.of(role));
//
//        when(passwordEncoder.encode("Test123$"))
//                .thenReturn("encodedPassword");
//
//        when(userRepository.save(any(User.class)))
//                .thenAnswer(invocation -> {
//                    User saved = invocation.getArgument(0);
//                    saved.setId(2L);
//                    return saved;
//                });
//
//        // Act
//        UserDto result = userService.createUser(dto, authentication);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2L, result.id());
//        assertEquals("john@mail.com", result.email());
//        assertEquals("johndoe", result.username());
//        assertTrue(result.isActive());
//        assertFalse(result.isAdmin());
//
//        verify(passwordEncoder).encode("Test123$");
//        verify(userRepository).save(any(User.class));
//    }

    @Test
    void createUser_ShouldThrowException_WhenPasswordsDoNotMatch() {

        // Arrange
        UserCreateDto dto = new UserCreateDto();

        dto.setPassword("Test123$");
        dto.setConfirmPassword("Wrong123$");

        when(securityService.getUserAuthentication(authentication))
                .thenReturn(currentUser);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(dto, authentication)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {

        // Arrange
        UserCreateDto dto = new UserCreateDto();

        dto.setPassword("Test123$");
        dto.setConfirmPassword("Test123$");
        dto.setEmail("john@mail.com");

        when(securityService.getUserAuthentication(authentication))
                .thenReturn(currentUser);

        when(userRepository.existsByEmail("john@mail.com"))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(dto, authentication)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenUsernameAlreadyExists() {

        // Arrange
        UserCreateDto dto = new UserCreateDto();

        dto.setPassword("Test123$");
        dto.setConfirmPassword("Test123$");
        dto.setEmail("john@mail.com");
        dto.setUsername("johndoe");

        when(securityService.getUserAuthentication(authentication))
                .thenReturn(currentUser);

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByUsername(dto.getUsername()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(dto, authentication)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenRoleIdsAreEmpty() {

        // Arrange
        UserCreateDto dto = new UserCreateDto();

        dto.setPassword("Test123$");
        dto.setConfirmPassword("Test123$");
        dto.setEmail("john@mail.com");
        dto.setUsername("johndoe");
        dto.setRoleIds(Collections.emptySet());

        when(securityService.getUserAuthentication(authentication))
                .thenReturn(currentUser);

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByUsername(dto.getUsername()))
                .thenReturn(false);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(dto, authentication)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    // ==========================================
    // UPDATE USER
    // ==========================================

//    @Test
//    void updateUser_ShouldUpdateUser_WhenUserExists() {
//
//        // Arrange
//        User existingUser = new User();
//
//        existingUser.setId(2L);
//        existingUser.setFirstName("John");
//        existingUser.setLastName("Doe");
//        existingUser.setEmail("john@mail.com");
//        existingUser.setUsername("johndoe");
//        existingUser.setIsActive(true);
//        existingUser.setIsAdmin(false);
//        existingUser.setRoles(new HashSet<>(Set.of(role)));
//
//        UserUpdateDto dto = new UserUpdateDto();
//
//        dto.setIsActive(false);
//        dto.setIsAdmin(true);
//        dto.setRoleIds(Set.of(1L));
//
//        when(securityService.getUserAuthentication(authentication))
//                .thenReturn(currentUser);
//
//        when(userRepository.findById(2L))
//                .thenReturn(Optional.of(existingUser));
//
//        when(roleRepository.findAllById(dto.getRoleIds()))
//                .thenReturn(List.of(role));
//
//        when(userRepository.save(any(User.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        UserDto result = userService.updateUser(
//                2L,
//                dto,
//                authentication
//        );
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2L, result.getId());
//        assertFalse(result.getIsActive());
//        assertTrue(result.getIsAdmin());
//
//        verify(userRepository).save(existingUser);
//    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {

        // Arrange
        UserUpdateDto dto = new UserUpdateDto();

        when(securityService.getUserAuthentication(authentication))
                .thenReturn(currentUser);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(
                        99L,
                        dto,
                        authentication
                )
        );

        verify(userRepository, never()).save(any(User.class));
    }

    // ==========================================
    // DELETE USER
    // ==========================================

    @Test
    void deleteUser_ShouldDeleteUser_WhenCalled() {

        // Arrange
        Long userId = 2L;

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).deleteById(userId);
    }
}
