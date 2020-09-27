package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.io.entity.UserEntity;


//Instantiating UserRepository that by default will hold CRUD operation methods for database manipulations
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
//Adding custom method that will check is the user with this email exists in the database
 UserEntity findByEmail(String email);
 UserEntity findByUserId(String userId);
}
