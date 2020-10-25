package com.crm.service.interfaces;

import java.util.List;

import com.crm.model.Client;


public interface IClient {
	
	public void saveClient(Client client);
	
	public List<Client> findAll();
		
	public void deleteById(Long id);
	
	public Client findById(Long id);
	
	public void updateClient(Client editedClient, Client editClient);
}
