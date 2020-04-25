package com.crm.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crm.domain.User;
import com.crm.domain.Role;
import com.crm.repository.RoleRepository;
import com.crm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService  {
	
	private  final String USER_ROLE = "USER";
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.roleRepository=roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if( user == null ){
			throw new UsernameNotFoundException(username);
		}
		
		return new UserDetailsImpl(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}



	@Override
	public void registerUser(User user) {
		Role userRole = roleRepository.findByRole(USER_ROLE);
		
		if(userRole!=null) {
			user.getRoles().add(userRole);
		}else{
			user.addRoles(USER_ROLE);
		}
			
		User u= userRepository.save(user);
	}

}
