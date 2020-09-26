package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.io.entity.UserEntity;


//Instantiating UserRepository that by default will hold CRUD operation methods for database manipulations
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	//Adding custom method that will check is the user with this email exists in the database to avoid duplicates
 UserEntity findByEmail(String email);
}
