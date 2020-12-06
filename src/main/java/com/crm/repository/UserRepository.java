package com.crm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	@Query(value = "SELECT email FROM users", nativeQuery = true)
	 List<String> allEmail();
	
	User findByActivation(String code);
	
	User findByToken(String token);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users_roles SET  role_id = ?2 WHERE user_id = ?1",nativeQuery = true)
	User updateUserRole( Long user_id,  Long role_id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM users_roles WHERE user_id = ?1",nativeQuery = true)
	void deleteUsers_Roles( Long user_id);

}
