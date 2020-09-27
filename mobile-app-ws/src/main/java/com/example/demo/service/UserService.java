package com.example.demo.service;

import com.example.demo.shared.dto.UserDto;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

//Interface for UserService
public interface UserService extends UserDetailsService {

//Default methods that need to be overridden
	UserDto createUser(UserDto user);

	UserDto getUser(String email);

	UserDto getUserByUserId(String id);

	UserDto updateUser(String userId, UserDto user);

	void deleteUser(String userId);

	List<UserDto> getUsers(int page, int limit);

}
