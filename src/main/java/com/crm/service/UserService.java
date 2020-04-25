package com.crm.service;

import com.crm.domain.User;

public interface UserService {

	public void registerUser(User user);
	public User findByEmail(String email);
}
