package com.net.blog.service;

import com.net.blog.dto.LoginDto;
import com.net.blog.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}
