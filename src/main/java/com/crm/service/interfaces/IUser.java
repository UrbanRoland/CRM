package com.crm.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.crm.domain.User;

public interface IUser {

	public void registerUser(User user);

	public User findByEmail(String email);

	public List<String> allEmail();
	
	public String userActivation(String code);
	
	public String forgotPassword(String email);
	
	public String resetPassword(String token, String password);
	
	public void updateUserName(String username, String email);
	
	public void updateUserPassword(String password, String passwordConf, String email);

	public void addPhoto(MultipartFile file) throws Exception;
	
	public   Optional<User> findById(Long id);
	
	
	
}
