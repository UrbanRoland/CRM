package com.crm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.crm.domain.Client;


public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findAll();
	
}