package com.example.demo.service.impl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.exceptions.UserServiceException;
import com.example.demo.io.entity.UserEntity;
import com.example.demo.io.repository.UserRepository;
import com.example.demo.shared.dto.UserDto;

import com.example.demo.shared.Utils;

class UserServiceImplTest {
//Mocking the used methods
	@InjectMocks
	UserServiceImpl userService;
	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
//declaring some universal variables
	String UserId = "jnhjcbwdhcbw";
	String Password = "kjndckwjncw";

	UserEntity userEntity;
//Will run before each test
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("George");
		userEntity.setLastName("Miller");
		userEntity.setUserId(UserId);
		userEntity.setEncryptedPassword(Password);
	}
//Testing  getUser Method
	@Test
	final void testGetUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("test@test.com");
		assertNotNull(userDto);
		assertEquals("George", userDto.getFirstName());

	}
//Testing Exception type in Get User
	@Test
	final void testGetUser_UsernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser("test@test.com");
		});
	}
//Testing Create User method
	@Test
	final void testCreateUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateUserId(anyInt())).thenReturn(UserId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(Password);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserDto userDto = new UserDto();
		UserDto storedUserDetails = userService.createUser(userDto);
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());

	}

@Test
//Testing Exception type in Create User
final void testCreateUser_CreateUserServiceException() {
	when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
	
	UserDto userDto = new UserDto();
	userDto.setFirstName("Alex");
	userDto.setLastName("Miller");
	userDto.setPassword(Password);
	userDto.setEmail("email@email.com");
	
	assertThrows(UserServiceException.class, ()-> {
		userService.createUser(userDto);
	});
}	

}
