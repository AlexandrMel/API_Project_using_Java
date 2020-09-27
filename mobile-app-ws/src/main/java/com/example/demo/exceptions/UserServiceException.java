package com.example.demo.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = 209507288353319104L;

public UserServiceException(String message)
{
	super(message);
}
}
