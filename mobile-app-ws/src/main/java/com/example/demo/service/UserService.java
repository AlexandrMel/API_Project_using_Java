package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.shared.dto.UserDto;

//Interface for UserService
public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);

}
