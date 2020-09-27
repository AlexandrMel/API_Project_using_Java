package com.example.demo.ui.controller;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserServiceException;
import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import com.example.demo.ui.model.request.RequestOperationName;
import com.example.demo.ui.model.request.UserDetailsRequestModel;
import com.example.demo.ui.model.response.ErrorMessages;
import com.example.demo.ui.model.response.OperationStatusModel;
import com.example.demo.ui.model.response.ResponseOperationStatus;
import com.example.demo.ui.model.response.UserRest;
import com.mysql.cj.xdevapi.Type;

import ch.qos.logback.classic.pattern.Util;

@RestController
@RequestMapping("users") // http:/localhost:8081/users
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {

		UserDto userDto = userService.getUserByUserId(id);

		UserRest returnValue = new UserRest();
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@PostMapping
	/*
	 * createUser is the main Method for creating the User, where userDetails is the
	 * incoming Body of the request to server and UserRest is the response that is
	 * send when the user is created
	 */

	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		if (userDetails.getFirstName() == null)
			throw new NullPointerException("The object is null");// UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		// Instantiate the UserRest model that contains predefined data field that will
		// be used to send back data
		UserRest returnValue = new UserRest();

		// Instantiate the general user Data transfer Object that will be shared between
		// layers
		UserDto userDto = new UserDto();

		// Populate the user Data transfer Object with data received in the request
		// body(userDetails)
		BeanUtils.copyProperties(userDetails, userDto);

		// Trigger to start adding the data from the userDto to Database through
		// userService
		UserDto createdUser = userService.createUser(userDto);
		// Populate the returnValue with properties received from database as response
		// to user being created;
		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;

	}

	@PutMapping(path = "/{id}")
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails ) {

		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto updatedUser = userService.updateUser(id, userDto);

		UserRest returnValue = new UserRest();
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationResult(ResponseOperationStatus.SUCCESS.name());
		return returnValue;

	
	}
@GetMapping
public List<UserRest> getUsers(@RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="limit", defaultValue="25") int limit){
	List<UserRest> returnValue = new ArrayList<UserRest>();
	List<UserDto> users = (List<UserDto>) userService.getUsers(page, limit);
	for (UserDto userDto : users) {
		UserRest userModel = new UserRest();
		BeanUtils.copyProperties(userDto, userModel);
		returnValue.add(userModel);
	}
	return returnValue;
}
}
