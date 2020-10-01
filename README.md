# API_Project_using_Java
An API with Authentication and Authorization build with Spring Boot and Java

API for managing users with authentication and authorization using JWT.
All endpoints are secured and only create new user does not require a valid token;

# Security


To register new users make a POST request to "/users/login" while providing username and password in the body
of the request. Example:
```
[POST] http://localhost:8081/users/login
{
    "username": "user",
    "password": "password"
}
```

<b>All the passwords are stored using BCrypt.</b>

### REST API endpoints ###
All of the available endpoints for the APi can be seen inside the "controller" package.

### Pre-requisite ###
To run the API, Java 11 or higher is mandatory, and Maven 3.6.0 or higher.

### Build the API and run it
Simply use Maven to create a runnable jar.-> "mvn package".
Run it using the terminal with the command "java -jar PACKAGE_NAME"

# ENDPOINTS

Here are examples how to interact with the API.

Change the base URL according to the actual URL and adjust the port if needed 
 * (application.properties -> server.port =YOUR_PORT)


### Users
Used for interacting with users

- Get all users using pagination
```
[GET] http://localhost:8081/users?page={PAGE_INDEX}}&limit={PAGE_LIMIT}
```
- Get specific users details
```
[GET] http://localhost:8081/users/{USER_ID}
```
- Create a new user
```
[POST] http://localhost:8081/users
{
	"firstName": FIRST_NAME,
	"lastName": LAST_NAME,
	"email": USER_EMAIL,
	"password": PASSWORD 
}
```
- Update user details
```
[PUT] http://localhost:8081/users/{USER_ID}
{
	"firstName": FIRST_NAME,
	"lastName": LAST_NAME,
	"email": USER_EMAIL,
	"password": PASSWORD 
}
```
- Delete a user
```
[DELETE] http://localhost:8081/users/{USER_ID}
```
