package com.crm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.crm.model.Role;
import com.crm.model.User;
import com.crm.repository.RoleRepository;
import com.crm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	private final Logger logger = Logger.getLogger("TestLogger");
	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private EmailServiceImpl emailService;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testfindById() {
		User u = new User();
		u.setId(1L);
		u.setEmail("test@gmail.com");
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(u));

		User user = userServiceImpl.findById(1L);
		assertThat(user.getId()).isEqualTo(1L);
	}

	@Test
	public void testfindAll() {

		List<User> datas = new ArrayList<>();
		User user = new User();
		user.setId(1L);
		user.setEmail("urolir@gmail.com");

		User user2 = new User();
		user.setId(1L);
		user.setEmail("urolir@gmail2.com");

		datas.add(user);
		datas.add(user2);

		Mockito.when(userRepository.findAll()).thenReturn(datas);

		List<User> expected = userServiceImpl.findAll();
		assertEquals(expected, datas);

	}

	@Test
	public void testfindByEmail() {
		User u = new User();
		u.setId(1L);
		u.setEmail("urolir@gmail.com");
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(u);

		User user = userServiceImpl.findByEmail(u.getEmail());
		assertThat(user.getEmail()).isEqualTo("urolir@gmail.com");
	}

	@Test
	public void testdeleteById() {

		final Long userId = 1L;

		userServiceImpl.deleteById(userId);
		userServiceImpl.deleteById(userId);

		verify(userRepository, times(2)).deleteById(userId);
	}

	@Test
	public void testsave() {

		User user = new User();
		user.setId(1L);
		user.setEmail("urolir@gmail.com");

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		User savedUser = userServiceImpl.save(user);

		assertThat(savedUser).isEqualToComparingFieldByField(user);
		Mockito.verify(userRepository).save(savedUser);
	}

	@Test
	public void testdeleteUsers_Roles() {
		final Long userId = 1L;

		userServiceImpl.deleteUsers_Roles(userId);
		userServiceImpl.deleteUsers_Roles(userId);

		verify(userRepository, times(2)).deleteUsers_Roles(userId);
	}

	@Test
	public void testfindByRoleName() {
		Role role = new Role();
		role.setId(1L);
		role.setRole("Test_Role");
		Mockito.when(roleRepository.findByRoleName(Mockito.anyString())).thenReturn(role.getId());

		Long id = userServiceImpl.findByRoleName(role.getRole());
		assertThat(id).isEqualTo(role.getId());
	}

	@Test
	public void testupdateUserPassword() {
		User user = new User();
		user.setId(1L);
		user.setEmail("urolir@gmail.com");
		user.setPassword("12345");
		user.setPasswordConf("12345");
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		User expected = new User();
		expected = userServiceImpl.updateUserPassword(expected, user);

		assertThat(expected).isNotNull();
		assertEquals(expected.getPassword(), user.getPassword(), "Not equals");
		assertEquals(expected.getPasswordConf(), user.getPasswordConf(), "Not equals");

	}
	@Test
	public void testloadUserByUsername() {
		User u=new User();
		u.setId(1L);
		u.setEmail("test@gmail.com");
		
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);
		Mockito.when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(u);
		
		User savedUser = new User();
		savedUser = userServiceImpl.save(u);
		
		
		UserDetails a= userServiceImpl.loadUserByUsername(savedUser.getEmail());
		
		assertThat(a).isNotNull();
	}
	
	@Test
	public void testregisterUser() {
		User u=new User();
		u.setId(1L);
		u.setEmail("test@gmail.com");
		
		Role role = new Role();
		role.setId(1L);
		role.setRole("Test_Role");
		
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);
		Mockito.when(roleRepository.findByRole(Mockito.any(String.class))).thenReturn(role);
		
		User savedUser = new User();
		savedUser = userServiceImpl.save(u);
		
		userServiceImpl.registerUser(savedUser);
		assertEquals(role.getRole().isEmpty(), savedUser.getRoles().isEmpty());
		assertEquals(role.getRole(), savedUser.getRoles().stream().findFirst().get().getRole());
		assertEquals(false, savedUser.getEnabled());
		assertEquals(false, savedUser.getImage());
		assertNotNull(savedUser.getActivation());
	}
	
	@Test
	public void testforgotPassword() {
		User u=new User();
		u.setId(1L);
		u.setEmail("test@gmail.com");
		
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);
		Mockito.when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(u);
		
		User savedUser = new User();
		savedUser = userServiceImpl.save(u);
		
		String expected=userServiceImpl.forgotPassword(savedUser.getEmail());
		assertNotEquals("Invalid", expected);
		assertNotNull(savedUser.getToken());
		assertNotNull(savedUser.getTokenCreationDate());
	}
	
	
	
	@Test
	public void testresetPassword() {
		User u=new User();
		u.setId(1L);
		u.setEmail("test@gmail.com");
		u.setTokenCreationDate(LocalDateTime.now());
		u.setToken("1a436824-e788-463e-8e69-a5437b2620e39fc035a3-acc8-4849-83be-26be40659b73");
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);
		Mockito.when(userRepository.findByToken(Mockito.any(String.class))).thenReturn(u);
		
		User savedUser = new User();
		savedUser = userServiceImpl.save(u);
		
		String result=userServiceImpl.resetPassword(savedUser.getToken(), "12345");
		assertNotEquals("Invalid token.", result);
		assertNotEquals("Token expired.", result);
		assertEquals("Your password successfully updated.", result);
		assertEquals(null, savedUser.getTokenCreationDate());
		assertEquals(null, savedUser.getToken());
		assertEquals("12345", savedUser.getPassword());
		assertEquals("12345", savedUser.getPasswordConf());
	}
	
	@Test
	public void testuserActivation() {
		String code="msygghlhtymjxdtm";
		User u=new User();
		u.setId(1L);
		u.setActivation(code);
		u.setEnabled(false);
		u.setEmail("test@gmail.com");
		
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);
		Mockito.when(userRepository.findByActivation(Mockito.any(String.class))).thenReturn(u);
		
		User savedUser = new User();
		savedUser = userServiceImpl.save(u);
	
		userServiceImpl.userActivation(code);
	
		
		assertEquals("", savedUser.getActivation(),"Not equals");
		
	}
	
	
	@Test
	public void testupdateUserNameAndEmail() {
		User user = new User();
		user.setId(1L);
		user.setEmail("urolir@gmail.com");
		user.setUsername("Test");
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		User expected = new User();
		expected = userServiceImpl.updateUserNameAndEmail(expected, user);

		assertThat(expected).isNotNull();
		assertEquals(expected.getUsername(), "Test", "Usernames are not equals");
		assertEquals(expected.getEmail(), user.getEmail(), "Emails are not equals");
		verify(userRepository).save(Mockito.any(User.class));
	}

}
