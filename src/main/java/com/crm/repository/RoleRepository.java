package com.crm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	
	@Query(value = "SELECT id FROM roles WHERE role= ?1", nativeQuery = true)
	 Long findByRoleName(String role);
	
}
