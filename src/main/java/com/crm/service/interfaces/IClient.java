package com.crm.service.interfaces;

import java.util.List;

import com.crm.domain.Client;


public interface IClient {
	
	public void saveClient(Client client);
	
	public List<Client> findAll();

}
