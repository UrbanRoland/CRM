package com.crm.service.interfaces;

import java.util.List;

import com.crm.model.Client;


public interface IClient {
	
	public Client saveClient(Client client);
	
	public List<Client> findAll();
		
	public void deleteById(Long id);
	
	public Client findById(Long id);
	
	public Client updateClient(Client editedClient, Client editClient);
}
