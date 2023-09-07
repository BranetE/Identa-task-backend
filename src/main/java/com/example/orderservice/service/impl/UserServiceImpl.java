package com.example.orderservice.service.impl;

import com.example.orderservice.dto.AuthResponseDto;
import com.example.orderservice.dto.CreateUserDto;
import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.model.Role;
import com.example.orderservice.model.User;
import com.example.orderservice.repository.UserRepository;
import com.example.orderservice.security.CustomUserDetails;
import com.example.orderservice.security.JwtProvider;
import com.example.orderservice.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider tokenGenerator;

    @Override
    public void createUser(CreateUserDto userDto) {

        if(userRepository.existsByEmail(userDto.email())){
            throw new EntityExistsException("User with that email already exists");
        }

        User user = User.builder()
                .email(userDto.email())
                .firstName(userDto.firstName())
                .role(Role.USER)
                .lastName(userDto.lastName())
                .password(passwordEncoder.encode(userDto.password()))
                .build();
        userRepository.save(user);
    }

    public AuthResponseDto login(LoginDto loginDto){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User authenticatedUser = principal.getUser();
        String token = tokenGenerator.generateToken(authenticatedUser);
        return new AuthResponseDto(token, "Bearer: ");
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
