package com.crm.service;

import java.util.List;

import com.crm.domain.User;

public interface UserService {

	public void registerUser(User user);

	public User findByEmail(String email);

	public List<String> allEmail();
	
	public String userActivation(String code);
	
	public String forgotPassword(String email);
	
	public String resetPassword(String token, String password);
	
	
	
}
