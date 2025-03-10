package com.movieapp.controller;

import com.movieapp.dto.*;
import com.movieapp.entity.User;
import com.movieapp.entity.UserRole;
import com.movieapp.exception.handling.BaseException;
import com.movieapp.exception.handling.enums.ErrorMessages;
import com.movieapp.repository.UserRepository;
import com.movieapp.repository.UserRoleRepository;
import com.movieapp.security.JwtUtils;
import com.movieapp.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Endpoints for User Authentication and Registration")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponse<Object>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(authentication);

            JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.isAdmin());
            return ResponseEntity.ok(new BaseResponse<>(jwtResponse));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(new MessageResponse(ex.getMessage()), false));
        } catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(new MessageResponse("Incorrect email or password!"), false));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<MessageResponse>> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BaseException(ErrorMessages.EMAIL_ALREADY_IN_USE);
        }

        UserRole role = userRoleRepository.findByRoleName("ROLE_USER");

        User user = new User(signupRequest.getUserName(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail(),
                role);
        userRepository.save(user);

        return ResponseEntity.ok(new BaseResponse<>(new MessageResponse("User registered successfully!")));
    }
}