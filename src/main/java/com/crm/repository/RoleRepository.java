package com.crm.repository;

import org.springframework.data.repository.CrudRepository;

import com.crm.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	
}
