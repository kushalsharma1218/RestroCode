package com.example.UserRegistrationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserRegistrationService.model.User;
import com.example.UserRegistrationService.repository.UserRepository;

@Service
public class UserService {

	@Autowired UserRepository userRepository;
	
	public User addUser(User user) 
	{
		userRepository.save(user);
		return user;
	}
	public boolean checkUserName(String username) 
	{
		User user1 = userRepository.findByUserName(username);

		if(user1 != null )return false;
		return true;
	}
	
	public boolean checkUserName(User user) 
	{
		User user1 = userRepository.findByUserName(user.getUserName());

		if(user1 != null )return false;
		return true;
	}
	public boolean checkEmail(User user) 
	{
		
		User user2 = userRepository.findByEmail(user.getEmail());
		if(user2 != null)return false;
		return true;
	}
}
