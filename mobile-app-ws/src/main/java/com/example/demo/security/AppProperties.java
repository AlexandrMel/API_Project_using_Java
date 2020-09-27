package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//Class that helps us access Environment variables from properties file, in this case the secret token
@Component
public class AppProperties {

	@Autowired
	private Environment env;

	public String getTokenSecret() {
		return env.getProperty("tokenSecret");
	}
}
