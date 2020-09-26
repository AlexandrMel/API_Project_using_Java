package com.example.demo.ui.controller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import com.example.demo.ui.model.request.UserDetailsRequestModel;
import com.example.demo.ui.model.response.UserRest;



@RestController
@RequestMapping("users") //http:/localhost:8081/users
public class UserController {

@Autowired
UserService userService;
	
@GetMapping
	public String getUser() {
		return "get user was called";
	}
@PostMapping
/*createUser is the main Method for creating the User, where userDetails is the incoming 
Body of the request to server and UserRest is the response that is send when the user is created */

	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
	
	// Instantiate the UserRest model that contains predefined data field that will be used to send back data
	UserRest returnValue = new UserRest();
			
	//Instantiate the general user Data transfer Object that will be shared between layers
	UserDto userDto = new UserDto();
	
	//Populate the user Data transfer Object with data received in the request body(userDetails)
	BeanUtils.copyProperties(userDetails, userDto);
	
	//Trigger to start adding the data from the userDto to Database through userService
	UserDto createdUser = userService.createUser(userDto);
	//Populate the returnValue with properties received from database as response to user being created;
	BeanUtils.copyProperties(createdUser, returnValue);
	
	return returnValue;
	
	}

@PutMapping
public String updateUser() {
	return "update user was called";
}

@DeleteMapping
public String deleteUser() {
	return "delete user was called";
}
}
