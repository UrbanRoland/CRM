package com.crm.service.interfaces;

import java.util.List;

import com.crm.model.Client;


public interface IClient {
	
	public void saveClient(Client client);
	
	public List<Client> findAll();
	public void updateClient( String address,  String city,  String contactPerson,String email, Long monthlyFee,String name,String phone,Long taxnumber,String zipCode,Long id);
	public void deleteById(Long id);
}
