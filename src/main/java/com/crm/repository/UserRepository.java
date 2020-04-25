package com.crm.repository;

import org.springframework.data.repository.CrudRepository;

import com.crm.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
}
