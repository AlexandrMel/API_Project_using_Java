package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.UserServiceException;
import com.example.demo.io.entity.UserEntity;
import com.example.demo.io.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;
import com.example.demo.shared.dto.UserDto;
import com.example.demo.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	// Importing UserRepositiry with its methods
	@Autowired
	UserRepository userRepository;

//Importing Utils with its methods
	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

//Overriding  methods declared in UserService Class
	//Create New User in the database
	@Override
	public UserDto createUser(UserDto user) {
		// using the custom created method of UserRepository class verify if the email
		// exists in the database and throw an exception with an explanatory message
		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new UserServiceException("Record already exists");

		// Instantiating a userEntity object from class and populating with the received
		// user data using Beans
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		// Generate public UserId using custom created Utils class methods
		String publicUserId = utils.generateUserId(30);
		// Setting the new generated userId to the userEntity object using a setter
		userEntity.setUserId(publicUserId);
		// Hashing the password before storing into database using BCrypt external
		// package
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		// Save the updated userEntity to database using UserRepository Class default
		// method (save) and store the return value
		UserEntity storedUserDetails = userRepository.save(userEntity);
		// Instantiating a return Value object from UserDto class and populating with
		// the storedUserDetails data using Beans
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		// return
		return returnValue;
	}

// Method to get user details by username, in our case it is the email
	@Override
	public UserDto getUser(String email) {
		// Getting the data from the database
		UserEntity userEntity = userRepository.findByEmail(email);
		// Throw exception if not found
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		// Create populate and return user details object
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	// Method to get specific user details by username, in our case it is the email
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Getting the data from the database
		UserEntity userEntity = userRepository.findByEmail(email);
		// Throw exception if not found
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		// Create populate and return user specific details object
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}
//Find a user in the database based on its UserId
	@Override
	public UserDto getUserByUserId(String userId) {
		// Getting the data from the database
		UserEntity userEntity = userRepository.findByUserId(userId);
		// Throw exception if not found
		if (userEntity == null)
			throw new UsernameNotFoundException("User with ID: " + userId + " not found");
		// Create populate and return user details object
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}
//Update a user entry in the database based on its UserId
	@Override
	public UserDto updateUser(String userId, UserDto user) {
		// Getting the data from the database
		UserEntity userEntity = userRepository.findByUserId(userId);
		// Throw exception if not found
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		UserEntity updatedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		return returnValue;
	}
//Delete a User from the Database
	@Override
	public void deleteUser(String userId) {
		// Getting the data from the database
		UserEntity userEntity = userRepository.findByUserId(userId);
		// Throw exception if not found
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(userEntity);

	}
//Get all Users from the database with pagination and a limit per page
	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
//Fixing 0 index to page 1 for better user experience;
		if (page > 0)
			page = page - 1;
//Create Pageable object for findAll method
		Pageable pageableRequest = PageRequest.of(page, limit);
//Get List of Users based on the Pageable Object configuration
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
//Loop through the list to create returning Array of UserDto type
		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
//Return the List
		return returnValue;
	}

}
