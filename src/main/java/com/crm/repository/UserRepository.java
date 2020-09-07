package com.crm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.crm.domain.Ticket;
import com.crm.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	List<User> findAll();
	
	@Query(value = "SELECT email FROM users", nativeQuery = true)
	 List<String> allEmail();
	
	User findByActivation(String code);
	User findByToken(String token);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users SET password =  ?1, password_conf = ?2 WHERE email = ?3",nativeQuery = true)
	void updateUserPassword( String password,  String passwordConf,  String email);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users SET username =  ?1 WHERE email = ?2",nativeQuery = true)
	void updateUserName( String username,  String email);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users_roles SET  role_id = ?2 WHERE user_id = ?1",nativeQuery = true)
	void updateUserRole( Long user_id,  Long role_id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM users_roles WHERE user_id = ?1",nativeQuery = true)
	void deleteUsers_Roles( Long user_id);

}
