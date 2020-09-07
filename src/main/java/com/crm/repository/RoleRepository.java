package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.crm.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	
	@Query(value = "SELECT id FROM roles WHERE role= ?1", nativeQuery = true)
	 Long findByRoleName(String role);
}
