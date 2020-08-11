package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.crm.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	@Query(value = "SELECT email FROM users", nativeQuery = true)
	 List<String> allEmail();
	
	User findByActivation(String code);
	User findByToken(String token);
}
