package com.example.orderservice.service;

import com.example.orderservice.ModelUtils;
import com.example.orderservice.dto.AuthResponseDto;
import com.example.orderservice.dto.CreateUserDto;
import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.model.User;
import com.example.orderservice.repository.UserRepository;
import com.example.orderservice.security.CustomUserDetails;
import com.example.orderservice.security.JwtProvider;
import com.example.orderservice.service.impl.UserServiceImpl;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider tokenGenerator;
    @Mock
    private Authentication auth;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegister(){
        CreateUserDto createUserDto = ModelUtils.getCreateUserDto();
        User user = ModelUtils.getUser();

        when(userRepository.existsByEmail("email@test.com")).thenReturn(false);
        when(passwordEncoder.encode("userpass")).thenReturn("userpass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(createUserDto);

        verify(userRepository).existsByEmail("email@test.com");
        verify(passwordEncoder).encode("userpass");
        verify(userRepository).save(any());
    }

    @Test
    void testRegisterExistingUser() {
        CreateUserDto createUserDto = ModelUtils.getCreateUserDto();

        when(userRepository.existsByEmail("email@test.com")).thenReturn(true);

        Exception assertedExc = assertThrows(EntityExistsException.class,() -> userService.createUser(createUserDto));
        assertEquals("User with that email already exists", assertedExc.getMessage());
        verify(userRepository).existsByEmail("email@test.com");
    }

    @Test
    void testLogin() {
        LoginDto loginDto = ModelUtils.getLoginDto();
        User user = ModelUtils.getUser();

        CustomUserDetails userDetails = new CustomUserDetails(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(tokenGenerator.generateToken(user)).thenReturn("random-token");
        when(auth.getPrincipal()).thenReturn(userDetails);
        AuthResponseDto actualResult = userService.login(loginDto);

        assertEquals("random-token", actualResult.getAccessToken());
//        assertEquals(UserDetailsDtoMapper.convertToDto(userDetails), actualResult.getUser());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenGenerator).generateToken(user);
        verify(auth).getPrincipal();
    }

    @Test
    void deleteUserTest(){
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
