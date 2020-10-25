package com.crm.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.crm.model.Role;
import com.crm.model.User;

public interface IUser {

	public void registerUser(User user);

	public User findByEmail(String email);

	public List<String> allEmail();
	
	public String userActivation(String code);
	
	public String forgotPassword(String email);
	
	public String resetPassword(String token, String password);
	
	public void updateUserNameAndEmail(User modifiledUser, User olderUser);
	
	public void updateUserPassword(User modifiledUser,User olderUser);
	
	public User findById(Long id);
	
	public List<User> findAll();
	
	public void updateUserRole(Long user_id,Long role_id);
	
	public Long findByRoleName(String role);
	
	public void deleteUsers_Roles( Long user_id);
	
	public void deleteById(Long id);
	
	public List<String> findRolesWithoutVezetoandUgyintezo();
	
	public void save(User u);

	public void addPhoto(MultipartFile file, User user) throws Exception;
	
}
