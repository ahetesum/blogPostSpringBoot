package com.net.blog.service.impl;

import com.net.blog.dto.LoginDto;
import com.net.blog.dto.RegisterDto;
import com.net.blog.entity.Role;
import com.net.blog.entity.User;
import com.net.blog.exception.BlogApiException;
import com.net.blog.repository.RoleRepository;
import com.net.blog.repository.UserRepository;
import com.net.blog.security.JwtTokenProvider;
import com.net.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;

    }


    @Override
    public String login(LoginDto loginDto) {
      Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        if(userRepository.existsByUsername(registerDto.getUsername()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Username already Exist");
        }
        if(userRepository.existsByEmail(registerDto.getEmail()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Email already Exist");
        }

        User user =new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles= new HashSet<>();
        Role userRole= roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);



        return "User register Successfully";
    }
}
