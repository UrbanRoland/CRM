package com.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

}
