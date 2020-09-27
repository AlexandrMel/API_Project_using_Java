package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.security.AppProperties;

@SpringBootApplication
public class MobileAppWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
	}
//Imported BCrypt so that we can wire it in the project
@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
}

//Imported Spring Application Context that help us get access to many resources throughout the project

@Bean
public SpringApplicationContext springApplicationContext() {
	return new SpringApplicationContext();
}

//Added AppProperties so that we can have a more secure access to the TOKEN SECRET,
@Bean(name="AppProperties")
public AppProperties getAppProperties()
{
	return new AppProperties();
}

}
