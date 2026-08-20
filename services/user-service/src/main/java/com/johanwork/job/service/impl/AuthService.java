package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.domain.UserRole;
import com.johanwork.job.dto.LoginRequest;
import com.johanwork.job.dto.SignupRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.UserMapper;
import com.johanwork.job.model.User;
import com.johanwork.job.dto.AuthResponse;
import com.johanwork.job.repository.UserRepository;
import com.johanwork.job.security.JwtUtil;
import com.johanwork.job.service.IAuthService;
import com.johanwork.job.service.IUserService;
import com.johanwork.job.util.Violations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public GenericResponse<AuthResponse> signup(SignupRequest request) {
        validateUser(request);
        User user = userMapper.mapRequestToEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(
               savedUser, request.password()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtil.generateToken(auth);
        return userMapper.mapEntityToAuthResponse(savedUser, token, AppConstant.Success.SIGNUP);
    }

    @Override
    public GenericResponse<AuthResponse> login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtUtil.generateToken(auth);
        User user = (User) auth.getPrincipal();
        return userMapper.mapEntityToAuthResponse(user, token, AppConstant.Success.LOGIN);
    }

    private void validateUser(SignupRequest req){
        Optional<User> userExist = userRepository.findByEmailOrPhone(req.email(), req.phone());
        Map<String, String> violations = new HashMap<>();
        if (userExist.isPresent()){
            User user = userExist.get();
            if (req.email().equalsIgnoreCase(user.getEmail())) violations.put("email","Email already exists");
            if (req.phone().equalsIgnoreCase(user.getPhone())) violations.put("phone","Phone already exists");
        }
        if (req.role() == UserRole.ROLE_ADMIN){
            violations.put("role","Role Admin not allowed");
        }
        var decision = compromisedPasswordChecker.check(req.password());
        if (decision.isCompromised()) violations.put("password", "Password compromised, choose a stronger password");
        if (!violations.isEmpty()) throw new CustomException(HttpStatus.BAD_REQUEST,
                AppConstant.Error.TITLE_BAD_REQUEST,
                AppConstant.Error.MESSAGE_BAD_REQUEST,
                violations);
    }
}
