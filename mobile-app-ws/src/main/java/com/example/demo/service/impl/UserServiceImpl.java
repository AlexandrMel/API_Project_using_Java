package com.example.demo.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.UserRepository;
import com.example.demo.io.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;
import com.example.demo.shared.dto.UserDto;



@Service
public class UserServiceImpl implements UserService {
	
	//Importing UserRepositiry with its methods
@Autowired
UserRepository userRepository;


//Importing Utils with its methods
@Autowired
Utils utils;

@Autowired
BCryptPasswordEncoder bCryptPasswordEncoder;

//Overriding createUser method declared in UserService Class
@Override
	public UserDto createUser(UserDto user) {
	//using the custom created method of UserRepository class verify if the email exists in the database and throw an exception with an explanatory message
	if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");
	
	// Instantiating a userEntity object from class and populating with the received user data using Beans
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
	//Gernerate public UserId using custom created Utils class methods
		String publicUserId = utils.generateUserId(30);
	//Setting the new generated userId to the userEntity object using a setter
		userEntity.setUserId(publicUserId);
	//Hashing the password before storing into database using BCrypt external package
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
	// Save the updated userEntity to database using  UserRepository Class default method (save) and store the return value
	UserEntity storedUserDetails = userRepository.save(userEntity);
	// Instantiating a return Value object from UserDto class and populating with the storedUserDetails data using Beans
	UserDto returnValue = new UserDto();
	BeanUtils.copyProperties(storedUserDetails, returnValue);
	
	//return
	return returnValue;
	}

@Override
public UserDto getUser(String email)
{
	UserEntity userEntity = userRepository.findByEmail(email);
	if(userEntity == null) throw new UsernameNotFoundException(email);
	
	UserDto returnValue = new UserDto();
	BeanUtils.copyProperties(userEntity, returnValue);
	return returnValue;
}


@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	UserEntity userEntity = userRepository.findByEmail(email);
	if(userEntity == null) throw new UsernameNotFoundException(email);
	return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
}

}
