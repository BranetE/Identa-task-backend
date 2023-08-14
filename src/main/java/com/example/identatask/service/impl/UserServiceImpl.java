package com.example.identatask.service.impl;

import com.example.identatask.dto.AuthResponseDto;
import com.example.identatask.dto.CreateUserDto;
import com.example.identatask.dto.LoginDto;
import com.example.identatask.dto.mapper.UserDetailsDtoMapper;
import com.example.identatask.model.Role;
import com.example.identatask.model.User;
import com.example.identatask.repository.UserRepository;
import com.example.identatask.security.CustomUserDetails;
import com.example.identatask.security.JwtTokenGenerator;
import com.example.identatask.service.UserService;
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
    private final JwtTokenGenerator tokenGenerator;

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
        String token = tokenGenerator.generateToken(authentication);
        return new AuthResponseDto(token, "Bearer: ", UserDetailsDtoMapper.convertToDto(principal));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
