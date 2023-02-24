package com.net.blog.controller;

import com.net.blog.dto.JWTAuthResponse;
import com.net.blog.dto.LoginDto;
import com.net.blog.dto.RegisterDto;
import com.net.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto)
    {
        String token=authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse=new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return  new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> login(@RequestBody RegisterDto registerDto)
    {
        String res=authService.register(registerDto);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
