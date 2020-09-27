package com.example.demo.exceptions;

// Instantiating  Exception from RuntimeException
public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 209507288353319104L;

	public UserServiceException(String message) {
		super(message);
	}
}
