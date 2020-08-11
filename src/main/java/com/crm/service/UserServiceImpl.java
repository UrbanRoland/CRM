package com.crm.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.domain.User;
import com.crm.domain.Role;
import com.crm.repository.RoleRepository;
import com.crm.repository.UserRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	private final String USER_ROLE = "Ügyintéző";
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private EmailService emailService;


	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,EmailService emailService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(user);
	
	}

	

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<String> allEmail() {
		return userRepository.allEmail();
	}

	@Override
	public void registerUser(User userToRegister) {
		User userCheck = userRepository.findByEmail(userToRegister.getEmail());

		// if (userCheck != null);

		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			userToRegister.getRoles().add(userRole);
		} else {
			userToRegister.addRoles(USER_ROLE);
		}
		String key = generateKey();
		userToRegister.setEnabled(false);
		userToRegister.setActivation(key);
		
	

		userRepository.save(userToRegister);
	    emailService.successRegistration(userToRegister.getEmail(),key);

	}

	//aktivaciohoz egy random kulcs
	public String generateKey() {
		String key = "";
		Random random = new Random();
		char[] word = new char[16];
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		String toReturn = new String(word);
		System.out.println("Aktivációs kód: " + toReturn);
		return new String(word);
	}

	@Override
	public String userActivation(String code) {
		User user = userRepository.findByActivation(code);
		if (user == null)
			return "noresult";

		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);

		return "ok";
	}

	@Override
	public String forgotPassword(String email) {
		
		Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));

		if (!userOptional.isPresent()) {
			return "Invalid";
		}

		User user = userOptional.get();
		user.setToken(generateToken());
		user.setTokenCreationDate(LocalDateTime.now());

		user = userRepository.save(user);
		//emailService.sendLinkToUser(email,user.getToken());
		System.out.println(user.getToken());
		return user.getToken();
	}

	@Override
	public String resetPassword(String token, String password) {

		Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));

		if (!userOptional.isPresent()) {
			return "Invalid token.";
		}
		
		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";
		}
		
		User user = userOptional.get();
		user.setPassword(password);
		user.setPasswordConf(password);
		user.setToken(null);
		user.setTokenCreationDate(null);
	
		userRepository.save(user);
		//emailService.changedPassword(user.getEmail());
		return "Your password successfully updated.";
	}

	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}

	private boolean isTokenExpired(LocalDateTime tokenCreationDate) {
		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;

	}

}
