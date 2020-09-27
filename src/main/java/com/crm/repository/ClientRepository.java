package com.crm.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.crm.model.Client;
import com.crm.model.User;


public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findAll();
	
	@Transactional
	@Modifying
	@Query(value="UPDATE client SET ADDRESS =  ?1, CITY = ?2, CONTACT_PERSON = ?3, EMAIL = ?4,  MONTHLY_FEE = ?5, NAME= ?6, 	PHONE  = ?7, 	TAXNUMBER  = ?8, ZIP_CODE   = ?9  WHERE ID = ?10",nativeQuery = true)
	void updateClient( String address,  String city,  String contactPerson,String email, Long monthlyFee,String name,String phone,Long taxnumber,String zipCode,Long id);
	
	
}
