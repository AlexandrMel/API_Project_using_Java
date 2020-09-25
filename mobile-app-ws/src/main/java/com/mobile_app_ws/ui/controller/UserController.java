package com.mobile_app_ws.ui.controller;


@RestController
@RequestMapping("users") //http://localhost:8080/users


public class UserController {
@GetMapping
	public String getUser() {
		return "get user was called";
	}
	
	public String createUser() {
		return "create user was called"
	}
}
