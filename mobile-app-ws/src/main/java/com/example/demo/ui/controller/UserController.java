package com.example.demo.ui.controller;

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

//Declaring Rest Controllers
@RestController
@RequestMapping("users") // http:/localhost:8081/users
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}") // users/:id
	public UserRest getUser(@PathVariable String id) {

		UserDto userDto = userService.getUserByUserId(id);

		UserRest returnValue = new UserRest();
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@PostMapping
	/*
	 * createUser route controller is the main Method for creating the User, where
	 * userDetails is the incoming Body of the request to server and UserRest is the
	 * response that is send when the user is created
	 */

	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		if (userDetails.getFirstName() == null)
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

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

//Route controller for Updating user details, in out case only firstName and lastName possible, because the rest is sensible data
	@PutMapping(path = "/{id}") // users/:id
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

//Populating userDto with request body data
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

//Getting the response from the service layer with updated data of the user from the database
		UserDto updatedUser = userService.updateUser(id, userDto);

//Populating returnValue with the data received from  the service layer
		UserRest returnValue = new UserRest();
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

//Route controller for Deleting a User from the database

	@DeleteMapping(path = "/{id}") // users/:id
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();

//There is no return statement here, we just send a custom response body with The method and Succes( should be improved and send back the data of the deleted user)
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationResult(ResponseOperationStatus.SUCCESS.name());
		return returnValue;

	}

//Route controller to get a List of All Users with pagination and limit filter per page
	@GetMapping // users
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		// Instantiate a List with specific type
		List<UserRest> returnValue = new ArrayList<UserRest>();

		// Get the list of users based on the page and limit from database through
		// service layer
		List<UserDto> users = (List<UserDto>) userService.getUsers(page, limit);

		// Loop through the list to form an updated list with UserRest model
		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}
}
