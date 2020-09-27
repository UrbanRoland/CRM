package com.crm.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.crm.model.User;

public interface IUser {

	public void registerUser(User user);

	public User findByEmail(String email);

	public List<String> allEmail();
	
	public String userActivation(String code);
	
	public String forgotPassword(String email);
	
	public String resetPassword(String token, String password);
	
	public void updateUserName(String username, String email);
	
	public void updateUserPassword(String password, String passwordConf, String email);

	public void addPhoto(MultipartFile file,User user) throws Exception;
	
	public Optional<User> findById(Long id);
	
	public List<User> findAll();
	
	public void updateUserRole(Long user_id,Long role_id);
	
	public Long findByRoleName(String role);
	
	public void deleteUsers_Roles( Long user_id);
	
	public void deleteById(Long id);
}
