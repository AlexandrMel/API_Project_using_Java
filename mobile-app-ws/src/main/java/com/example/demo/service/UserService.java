package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.shared.dto.UserDto;

//Interface for UserService
public interface UserService extends UserDetailsService {
//Self explanatory
	UserDto createUser(UserDto user);
	UserDto getUser(String  email);
	UserDto getUserByUserId(String id);

}
